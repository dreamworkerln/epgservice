package epg.server.entities.base.param;

import epg.protocol.dto.base.param.GetByIntervalDto;
import epg.server.entities.base.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;


@Component
public class GetByIntervalMapper extends AbstractMapper<GetByInterval, GetByIntervalDto> {

    GetByIntervalMapper() {
        super(GetByInterval.class, GetByIntervalDto.class);
    }


    @PostConstruct
    protected void setupMapper() {

        mapper.createTypeMap(GetByInterval.class, GetByIntervalDto.class).addMappings(
                mapper -> {
                    mapper.skip(GetByIntervalDto::setStart);
                    mapper.skip(GetByIntervalDto::setEnd);
                }).setPostConverter(getToDtoConverter());


        mapper.createTypeMap(GetByIntervalDto.class, GetByInterval.class).addMappings(
                mapper -> {
                    mapper.skip(GetByInterval::setStart);
                    mapper.skip(GetByInterval::setEnd);
                }).setPostConverter(getToEntityConverter());

    }

    @Override
    protected void mapSpecificFields(GetByInterval source, GetByIntervalDto destination) {
        destination.setStart(source.getStart().getEpochSecond());
        destination.setEnd(source.getEnd().getEpochSecond());
    }

    @Override
    protected void mapSpecificFields(GetByIntervalDto  source, GetByInterval destination) {
        destination.setStart(Instant.ofEpochSecond(source.getStart()));
        destination.setEnd(Instant.ofEpochSecond(source.getEnd()));
    }


}
