package epg.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import epg.protocol.dto.base.jrpc.AbstractDto;
import epg.protocol.dto.base.jrpc.JrpcData;
import epg.protocol.dto.base.jrpc.JrpcRequestHeader;
import epg.protocol.dto.base.http.HttpResponse;
import epg.protocol.dto.base.http.HttpResponseError;
import epg.protocol.dto.base.http.HttpResponseJRPC;
import epg.server.handlers.base.JrpcController;
import epg.server.handlers.base.JrpcHandler;
import epg.server.handlers.base.JrpcMethodHandler;
import epg.server.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@RestController
public class ApiController {

    private final ApplicationContext context;

    private final ObjectMapper objectMapper;

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final static String ACCESS_TOKEN = "f229fbea-a4b9-40a8-b8ee-e2b47bc1391d";

    private final Map<String, JrpcMethodHandler> handlers = new ConcurrentHashMap<>();

    @Autowired
    public ApiController(ApplicationContext context, ObjectMapper objectMapper) {
        this.context = context;
        this.objectMapper = objectMapper;

        // scanning handlers
        loadHandlers();
    }

    @RequestMapping(value="/api", method = RequestMethod.POST)
    //public UserDto register(@RequestBody UserByNickAndMail request)

    public ResponseEntity processRequest(@RequestBody String json) {

        // testing:



        // DEBUG
        // java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dserver.port=8081 -jar chuck-api-1.0.jar


        Long id = null;

        // json-rpc response
        AbstractDto jrpcResponse;

        // http response
        HttpResponse httpResponse;

        // result json string
        String result;

        // result status
        HttpStatus httpStatus;

        log.trace("POST '/api': " + json);


        // Пилим аналог
        // https://spring.io/guides/tutorials/bookmarks/
        // Только json-rpc
        // соответственно http path один, а method указывается внутри json
        // => самодельный routing (аналог @GetMapping, будем маршрутизировать по именам методов)

        // now simply convert your JSON string into your UserByNickAndMail POJO
        // using Jackson's ObjectMapper.readValue() method, whose first
        // parameter your JSON parameter as String, and the second
        // parameter is the POJO class.

        try {

            // get json-rpc request header (contains only method, token, id)
            //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JrpcRequestHeader jsonRpcHeader = objectMapper.readValue(json, JrpcRequestHeader.class);

            id = jsonRpcHeader.getId();
            final String method = jsonRpcHeader.getMethod();
            final String token = jsonRpcHeader.getToken();

            // ------------------------------------------------------------------------------
            // Parsing jrpc header

            if (id == null) {
                throw new IllegalArgumentException("JRPC no id specified");
            }
            if (Utils.isNullOrEmpty(token) || !token.equals(ACCESS_TOKEN)) {
                throw new IllegalAccessException("Forbidden");
            }
            if (Utils.isNullOrEmpty(method)) {
                throw new IllegalArgumentException("No JRPC method specified");
            }
            if (!handlers.containsKey(method)) {
                throw new IllegalArgumentException("JRPC method not found");
            }

            // ------------------------------------------------------------------------------
            //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            JsonNode jrpcParams = objectMapper.readTree(json).get("params");

            // ------------------------------------------------------------------------------


            JrpcMethodHandler handler = handlers.get(method);

            // executing RPC
            jrpcResponse = handler.apply(jrpcParams);

            // all going ok, prepare response
            httpResponse = new HttpResponseJRPC(jrpcResponse);
            httpResponse.setStatus(HttpStatus.OK);

        }
        catch (IllegalArgumentException | JsonProcessingException e) {

            log.error("",e);

            String message = e.getMessage();
            if(Utils.isNullOrEmpty(message)) {
                message = HttpStatus.BAD_REQUEST.name();
            }
            httpResponse = new HttpResponseError(message, HttpStatus.BAD_REQUEST);
        }
        catch (IllegalAccessException e) {
            log.error("",e);
            httpResponse = new HttpResponseError(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            log.error("",e);
            httpResponse = new HttpResponseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // add request id (if have one)
        httpResponse.setId(id);

        // Result serialization
        try {

            httpStatus = httpResponse.getStatus();
            // jsonObject to String
            result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(httpResponse);
        } catch (JsonProcessingException e) {
            log.error("Can't serialize httpResponse to json", e);
            httpResponse = new HttpResponseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(httpResponse, httpResponse.getStatus());
        }

        return new ResponseEntity<>(result, httpStatus);
    }

    // =================================================================================================

    /**
     * Fill requestHandlerList based on classes located in entities.message.request.*
     * <br>
     * Using reflection get all classes on server that handle clients requests
     */
    private void loadHandlers() {

        try {


            // Ask spring to load and find all beans with
            Map<String,Object> beans = context.getBeansWithAnnotation(JrpcController.class);
            //beans.keySet().forEach(System.out::println);

            // https://stackoverflow.com/questions/27929965/find-method-level-custom-annotation-in-a-spring-context
            for (Map.Entry<String, Object> entry : beans.entrySet()) {

                Object bean = entry.getValue();
                Class<?> beanClass = bean.getClass();

                JrpcController jrpcController = beanClass.getAnnotation(JrpcController.class);

                // Ищем в бине метод, помеченный аннотацией @JrpcHandler
                for (Method method : beanClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(JrpcHandler.class)) {

                        //Should give you expected results
                        JrpcHandler jrpcHandler = method.getAnnotation(JrpcHandler.class);


                        JrpcMethodHandler handler = jsonNode -> {
                            try {
                                return (AbstractDto)method.invoke(bean, jsonNode);
                            } catch (IllegalAccessException | InvocationTargetException e) {

                                // выбрасываем наверх причину InvocationTargetException -
                                // Что-то там упало в обработчике метода jrpc
                                // upcast into unchecked exception
                                if (e.getCause() != null) {
                                    throw (RuntimeException) e.getCause();
                                }
                                else {
                                    throw new RuntimeException(e);
                                }
                            }
                        };

                        handlers.put(jrpcController.path() + "." + jrpcHandler.method(), handler);
                    }
                }

            }

            //handlers.put("ololo", this::foo);

            //JrpcHandler handler = AnnotationUtils.findAnnotation(null, JrpcHandler.class);


//            for (Map.Entry<String, Object> entry : beans.entrySet()) {
//
//                JrpcController annotation = context.findAnnotationOnBean(entry.getKey(), JrpcController.class);
//
//                // reference to handler method
//                //noinspection unchecked
//                MethodHandler_OLD methodHandlerOLD = (MethodHandler_OLD)entry.getValue();
//                //RequestHandler_NOTUSED handler = new RequestHandler_NOTUSED(methodReference, annotation.request());
//
//                handlers.put(annotation.method(), methodHandlerOLD);
//
//            }


        } catch (BeansException e) {
            throw new RuntimeException(e);
        }

    }


    @Scheduled(cron = "*/3 * * * * *")
    //@Scheduled(fixedRate = 5000)
    public void scheduleTaskUsingCronExpression() throws InterruptedException {

        long now = Instant.now().getEpochSecond();
        System.out.println(
                "ОЧКО - schedule tasks using cron jobs - " + now);

        TimeUnit.DAYS.sleep(100000);
    }


    @Scheduled(cron = "*/5 * * * * *")
    //@Scheduled(fixedRate = 5000)
    public void scheduleTaskUsingCronExpression2() throws InterruptedException {

        long now = Instant.now().getEpochSecond();
        System.out.println(
                "ЗАЛУПА - schedule tasks using cron jobs - " + now);

        TimeUnit.DAYS.sleep(100000);

    }



//    private JrpcData foo(JsonNode node) {
//        System.out.println("Ololo!!!");
//
//        return null;
//    }






}


/*
        try {

            Reflections ref = new Reflections("hello");

            for (Class<?> cl : ref.getTypesAnnotatedWith(JrpcController.class)) {


                JrpcController apiHandlerAnt = cl.getAnnotation(JrpcController.class);
                Constructor<?> ctor = cl.getConstructor();

                //noinspection unchecked
                Function<JsonNode,ResponseBase> handler = (Function<JsonNode, ResponseBase>) ctor.newInstance();

                handlers.put(apiHandlerAnt.method(), handler);
            }

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }*/
