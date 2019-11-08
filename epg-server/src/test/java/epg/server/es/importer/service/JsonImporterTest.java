package epg.server.es.importer.service;


import epg.server.configuration.SpringConfiguration;
import epg.server.es.importer.mapper.ChannelESMapper;
import epg.server.es.importer.mapper.ProgramESMapper;
import epg.server.repository.ProgramGuideRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest(classes = {SpringConfiguration.class, ProgramGuideRepositoryImpl.class, ChannelESMapper.class,
        JsonImporter.class, ProgramESMapper.class})
class JsonImporterTest {

    @Autowired
    JsonImporter importer;



    @Test
    void apply() throws Exception {

        // Emulation
        String json = new String(Files.readAllBytes(Paths.get("data/local.json")), StandardCharsets.UTF_8);

        importer.process(json);

    }
}