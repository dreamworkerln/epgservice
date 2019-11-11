package epg.server.handlers.programguide;

import com.fasterxml.jackson.databind.JsonNode;
import epg.protocol.dto.base.jrpc.AbstractDto;
import epg.protocol.dto.base.param.GetByIntervalDto;
import epg.protocol.dto.programguide.ProgramGuideDto;
import epg.server.configuration.SpringConfiguration;
import epg.server.entities.base.param.GetByInterval;
import epg.server.entities.channel.Channel;
import epg.server.entities.programguide.ProgramGuide;
import epg.server.entities.program.Program;
import epg.server.handlers.base.JrpcController;
import epg.server.handlers.base.JrpcHandler;
import epg.server.handlers.base.MethodHandlerBase;
import epg.server.repository.ProgramGuideRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.concurrent.ConcurrentNavigableMap;


/**
 * Обработчик запроса - дай мне программу передач
 * ProgramGuide handler
 */
@Component
@JrpcController(path = SpringConfiguration.Controller.Handlers.Epg.PROGRAM_GUIDE)
public class ProgramGuideHandler extends MethodHandlerBase {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ProgramGuideRepository programGuideRepository;
    private final ModelMapper mapper;
    //private final ProgramGuideMapper programMapper;
    //private final GetByIntervalMapper getByIntervalMapper;



    @Autowired
    public ProgramGuideHandler(ProgramGuideRepository programGuideRepository,
                               ModelMapper mapper) {
        this.programGuideRepository = programGuideRepository;
        this.mapper = mapper;
    }


    @JrpcHandler(method = "getByInterval")
    public AbstractDto getByInterval(JsonNode params) {

        ProgramGuide progGuide = programGuideRepository.get();

        GetByInterval request;
        ProgramGuide pgResult;
        AbstractDto result;


        // parsing request
        try {

            GetByIntervalDto requestDto = objectMapper.treeToValue(params, GetByIntervalDto.class);
            request = mapper.map(requestDto, GetByInterval.class);
        }
        // All parse/deserialize errors interpret as 400 error
        catch (Exception e) {
            throw new IllegalArgumentException("Error parsing request: " + params.toPrettyString(), e);
        }

        // validate request
        GetByInterval.validate(request);


        // Getting from ProgramGuideRepository programs of all channels between
        // (request.start; request.end)

        pgResult = new ProgramGuide();

        // не хочу сперва заносить все каналы в result, т.к. тогда туда
        // могут попасть каналы без программ

        // перечисляем все каналы
        progGuide.getChannelList().forEach( (i, ch) -> {

            // укладываем этот канал(только канал, без программ) в result
            pgResult.getChannelList().putIfAbsent(i, new Channel(i, ch.getName()));

            // для каждого канала берем передачи, попадающие в замкнутый интервал
            ConcurrentNavigableMap<Instant,Program> map =
                    ch.getProgramList().subMap(request.getStart(), true,
                            request.getEnd(), true);

            // укладываем эту передачу в результат
            pgResult.getChannelList().get(i).getProgramList().putAll(map);
        });

        try {
            // wrapping to DTO
            result = mapper.map(pgResult, ProgramGuideDto.class);
        }
        // А если тут упало  - это уже 500
        catch (Exception e) {
            throw new RuntimeException("ProgramGuideHandler result mapping error: ", e);
        }
        return result;
    }

}
