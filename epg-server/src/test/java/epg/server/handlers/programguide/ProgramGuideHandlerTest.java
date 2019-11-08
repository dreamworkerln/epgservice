package epg.server.handlers.programguide;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import epg.protocol.dto.base.jrpc.JrpcRequest;
import epg.protocol.dto.base.param.GetByInterval;
import epg.server.TestSuite;
import epg.server.configuration.SpringConfiguration;
import epg.server.entities.programguide.ProgramGuideMapper;
import epg.server.es.importer.mapper.ChannelESMapper;
import epg.server.es.importer.mapper.ProgramESMapper;
import epg.server.es.importer.service.JsonImporter;
import epg.server.repository.ProgramGuideRepositoryImpl;
import epg.server.utils.Rest;
import epg.server.utils.RestFactory;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SpringConfiguration.class, ProgramGuideMapper.class, GetByInterval.class,
        JrpcRequest.class})
class ProgramGuideHandlerTest {

    private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Rest rest; // Wrapper of RestTemplate

    @Autowired
    private ApplicationContext context;


    @BeforeAll
    static void setup() {
        TestSuite.INSTANCE.init();
        rest = RestFactory.getRest(true, true);
    }


    @Test
    void getByInterval() throws Exception {


        GetByInterval getByInterval = context.getBean(GetByInterval.class);
        ObjectMapper objectMapper = context.getBean(ObjectMapper.class);

        getByInterval.setStart(Instant.parse("2000-12-03T10:15:30.00Z"));
        getByInterval.setEnd(Instant.parse("3007-12-03T10:15:30.00Z"));

        JrpcRequest jrpcRequest = context.getBean(JrpcRequest.class);
        jrpcRequest.setId(22L);
        jrpcRequest.setToken("f229fbea-a4b9-40a8-b8ee-e2b47bc1391d");

        String methodName = GetByInterval.class.getSimpleName();
        methodName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
        jrpcRequest.setMethod(SpringConfiguration.Controller.Handlers.Epg.PROGRAM_GUIDE + "." + methodName);

        jrpcRequest.setParams(getByInterval);

        String json = objectMapper.writeValueAsString(jrpcRequest);

        log.info(json);

        //json = String.format(json, id);

        ResponseEntity<String> response = rest.post("http://localhost:8085/api", json);
        log.info(response.getStatusCode().toString() + "\n" + response.getBody());

        JSONObject jsonObject = new JSONObject(response.getBody());
        String prettyFormated = jsonObject.toString(4);



        Files.write(Paths.get("data/response.json"),
                prettyFormated.getBytes(StandardCharsets.UTF_8));

    }
}