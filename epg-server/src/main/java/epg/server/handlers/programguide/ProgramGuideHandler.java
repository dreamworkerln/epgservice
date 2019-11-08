package epg.server.handlers.programguide;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import epg.protocol.dto.base.jrpc.AbstractDto;
import epg.protocol.dto.base.jrpc.JrpcData;
import epg.protocol.dto.base.param.GetByInterval;
import epg.protocol.dto.programguide.ProgramGuideDto;
import epg.server.configuration.SpringConfiguration;
import epg.server.entities.channel.Channel;
import epg.server.entities.programguide.ProgramGuide;
import epg.server.entities.program.Program;
import epg.server.entities.programguide.ProgramGuideMapper;
import epg.server.handlers.base.JrpcController;
import epg.server.handlers.base.JrpcHandler;
import epg.server.handlers.base.MethodHandlerBase;
import epg.server.repository.ProgramGuideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.concurrent.ConcurrentNavigableMap;


/**
 * Обраюотчик рапроса - дай мне программу передач
 * ProgramGuide handler
 */
@Component
@JrpcController(path = SpringConfiguration.Controller.Handlers.Epg.PROGRAM_GUIDE)
public class ProgramGuideHandler extends MethodHandlerBase {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ProgramGuideRepository programGuideRepository;
    private final ProgramGuideMapper mapper;



    @Autowired
    public ProgramGuideHandler(ProgramGuideRepository programGuideRepository, ProgramGuideMapper mapper) {
        this.programGuideRepository = programGuideRepository;
        this.mapper = mapper;
    }


    @JrpcHandler(method = "getByInterval")
    public JrpcData getByInterval(JsonNode params) {

        ProgramGuide progGuide = programGuideRepository.get();

        GetByInterval request;
        ProgramGuide result;
        JrpcData resultDto;


        try {
            request = objectMapper.treeToValue(params, GetByInterval.class);
        } catch (JsonProcessingException e) {
            log.error("json parse error: " + params.toPrettyString(), e);
            throw new IllegalArgumentException(e);
        }

        if (request == null) {
            throw new IllegalArgumentException("params == null");
        }


        // Getting from ProgramGuideRepository programs of all channels between
        // (request.start; request.end)

        result = new ProgramGuide();

        // не хочу сперва заносить все каналы в result, т.к. тогда туда
        // могут попасть каналы без программ

        ConcurrentNavigableMap<Integer,Channel> channelList = result.getChannelList();
        // перечисляем все каналы
        progGuide.getChannelList().forEach( (i, ch) -> {

            // укладываем этот канал в result
            result.getChannelList().putIfAbsent(i, ch);

            // для каждого канала берем передачи, попадающие в замкнутый интервал
            ConcurrentNavigableMap<Instant,Program> map =
                    ch.getProgramList().subMap(request.getStart(), true,
                            request.getEnd(), true);

            // укладываем эту передачу в результат
            result.getChannelList().get(i).getProgramList().putAll(map);
        });

        try {
            // Обертка
            resultDto = new AbstractDto() {
                private ProgramGuideDto programGuide;

                {
                    programGuide = mapper.toDto(result);
                }

                public ProgramGuideDto getProgramGuide() {
                    return programGuide;
                }

                public void setProgramGuide(ProgramGuideDto programGuide) {
                    this.programGuide = programGuide;
                }
            };


        } catch (Exception e) {
            log.error("ModelMapper error", e);
            throw new IllegalArgumentException(e);
        }
        return resultDto;
    }

}
