package epg.server.es.importer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import epg.server.entities.channel.Channel;
import epg.server.entities.program.Program;
import epg.server.entities.programguide.ProgramGuide;
import epg.server.es.importer.dto.channel.ChannelESDto;
import epg.server.es.importer.dto.program.ProgramESDto;
import epg.server.es.importer.mapper.ChannelESMapper;
import epg.server.es.importer.mapper.ProgramESMapper;
import epg.server.repository.ProgramGuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentNavigableMap;

@Service
public class JsonImporter {

    private final ProgramGuideRepository programGuideRepository;
    private final ChannelESMapper channelESMapper;
    private final ProgramESMapper programESMapper;

    @Autowired
    public JsonImporter(ProgramGuideRepository programGuideRepository,
                        ChannelESMapper channelESMapper,
                        ProgramESMapper programESMapper) {
        this.programGuideRepository = programGuideRepository;
        this.channelESMapper = channelESMapper;
        this.programESMapper = programESMapper;
    }

    public void process(String json) throws Exception {

        try {

            ProgramGuide progGuide = programGuideRepository.get();

            progGuide.getChannelList().clear();
            ConcurrentNavigableMap<Integer, Channel> channelList = progGuide.getChannelList();


            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            JsonNode root = objectMapper.readTree(json);


            // Import channels
            JsonNode channelsJson = root.get("tv").get("channel");

            for (int i = 0; i < channelsJson.size(); i++) {
                ChannelESDto channelESDto = objectMapper.treeToValue(channelsJson.get(i), ChannelESDto.class);
                Channel channel = channelESMapper.toEntity(channelESDto);

                channelList.put(channel.getId(), channel);
            }


            // Import programs
            JsonNode programsJson = root.get("tv").get("programme");
            //ConcurrentNavigableMap<Integer, Channel> channelList = progGuide.getChannelList();
            for (int i = 0; i < programsJson.size(); i++) {

                ProgramESDto programESDto = objectMapper.treeToValue(programsJson.get(i), ProgramESDto.class);
                Program program = programESMapper.toEntity(programESDto);

                Channel channel = program.getChannel(); // already asserted in ProgramESMapper

                channel.getProgramList().put(program.getStart(), program);
            }


            System.out.println(progGuide.getChannelList().size());

        }
        catch (Exception e) {
            throw e;
        }

        
    }
}
