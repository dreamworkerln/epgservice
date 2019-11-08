package epg.server;

import epg.server.es.importer.service.JsonImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class AppStartupRunner implements ApplicationRunner, Closeable {

    private final JsonImporter importer;

    @Autowired
    public AppStartupRunner(JsonImporter importer) {
        this.importer = importer;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        String json = new String(Files.readAllBytes(Paths.get("data/local.json")), StandardCharsets.UTF_8);
        importer.process(json);
    }



    @Override
    public void close() throws IOException {

    }

}
