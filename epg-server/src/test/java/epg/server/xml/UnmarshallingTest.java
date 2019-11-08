package epg.server.xml;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import epg.protocol.dto.base.jrpc.JrpcRequest;
import epg.server.es.importer.dto.program.ProgramESDto;
import epg.server.TestSuite;
import epg.server.utils.Rest;
import epg.server.utils.RestFactory;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

@SpringBootTest(classes = {ObjectMapper.class, JrpcRequest.class})
public class UnmarshallingTest {

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
    void xmlToJson() throws IOException {

        String xml = new String(Files.readAllBytes(Paths.get("data/local.xml")), StandardCharsets.UTF_8);

        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        String jsonPrettyPrintString = xmlJSONObj.toString(4);

        Files.write(Paths.get("data/local.json"),
                jsonPrettyPrintString.getBytes(StandardCharsets.UTF_8));

    }

    @Test
    void mapJson() throws Exception {

        Set<String> ratings = new TreeSet<>();
        Set<String> countries = new TreeSet<>();

        Set<String> iconPosition = new TreeSet<>();
        Set<String> iconType = new TreeSet<>();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        String json = new String(Files.readAllBytes(Paths.get("data/local.json")), StandardCharsets.UTF_8);

        JsonNode root = objectMapper.readTree(json);//.get("tv").get("programme");

        JsonNode programJson = root.get("tv").get("programme");

        int index = -1;


        try {

            for (int i = 0; i < programJson.size(); i++) {



                index = i;

                //System.out.println(programJson.get(29).toPrettyString());

                ProgramESDto programESDto = objectMapper.treeToValue(programJson.get(i), ProgramESDto.class);

                //System.out.println(programESDto.toString());

                ratings.add(programESDto.getRating().getValue());

                programESDto.getCountry().forEach(t -> countries.add(t.getContent()));

                programESDto.getIcon().forEach(j -> iconPosition.add(j.getPosition()));
                programESDto.getIcon().forEach(k -> iconType.add(k.getType()));

            }
        }
        catch(Exception e) {
            System.out.println(e);
            System.out.println("AT: " + index);
            throw e;
        }

        //System.out.println(ratings);
        //System.out.println(countries);

        System.out.println(iconPosition); // => [gorizotal]
        System.out.println(iconType);     // => [frame, poster]



        //System.out.println(Program.toPrettyString());

        //ProgramESDto Program = objectMapper.readValue(json, ProgramESDto.class);






    }

}
