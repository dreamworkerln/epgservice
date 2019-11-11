package epg.server.entities.channel;

import epg.protocol.dto.channel.ChannelDto;
import epg.protocol.dto.program.ProgramDto;
import epg.server.entities.base.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ChannelMapper extends AbstractMapper<Channel, ChannelDto> {

    //private ProgramMapper programMapper;

    ChannelMapper() {
        super(Channel.class, ChannelDto.class);
    }

//    @Autowired
//    public final void setMapper(ProgramMapper programMapper) {
//        this.programMapper = programMapper;
//    }

    @PostConstruct
    protected void setupMapper() {

        mapper.createTypeMap(Channel.class, ChannelDto.class).addMappings(
                mapper -> {
                    mapper.skip(ChannelDto::setProgramList);
                }).setPostConverter(getToDtoConverter());


        mapper.createTypeMap(ChannelDto.class, Channel.class).addMappings(
                mapper -> {
                }).setPostConverter(getToEntityConverter());

    }

    @Override
    protected void mapSpecificFields(Channel source, ChannelDto destination) {
        source.getProgramList().forEach((i, p) -> {
            //ProgramDto pd = programMapper.toDto(p);
            ProgramDto pd = mapper.map(p, ProgramDto.class);
            destination.getProgramList().put(p.getStart().getEpochSecond(), pd);
        });
    }

    @Override
    protected void mapSpecificFields(ChannelDto  source, Channel destination) {
    }


}

