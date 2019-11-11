package epg.server.entities.programguide;

import epg.protocol.dto.channel.ChannelDto;
import epg.protocol.dto.programguide.ProgramGuideDto;
import epg.server.entities.base.mapper.AbstractMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProgramGuideMapper extends AbstractMapper<ProgramGuide, ProgramGuideDto> {

    ProgramGuideMapper() {
        super(ProgramGuide.class, ProgramGuideDto.class);
    }

    @PostConstruct
    protected void setupMapper() {

                mapper.createTypeMap(ProgramGuide.class, ProgramGuideDto.class).addMappings(
                        // назначаем маппинг полей (для final типов map полей нельзя указывать - не поддерживается блевотекой)
                        // соответственно отключаем маппинг для полей, которые не совпадают по типам
                        mapper -> {

                            //mapper.skip(ProgramGuideDto::setChannelList);

                            //mapper.skip(OrderDto::setDate);
                            // такие типы (поля) конвертируются потом отдельно в пост-конвертере, конкретно тут - в mapSpecificFields
                        }).setPostConverter(getToDtoConverter());


        mapper.createTypeMap(ProgramGuideDto.class, ProgramGuide.class).addMappings(
                mapper -> {
                    //mapper.skip(Order::setDate);
                }).setPostConverter(getToEntityConverter());
    }

    @Override
    protected void mapSpecificFields(ProgramGuide source, ProgramGuideDto destination) {
        //destination.setDate(source.getDate().getEpochSecond());

//        source.getChannelList().forEach((i, ch) -> {
//
//            ChannelDto chd = new ChannelDto(i, ch.getName());
//            destination.getChannelList().put(i, chd);
//        });

    }

    @Override
    protected void mapSpecificFields(ProgramGuideDto source, ProgramGuide destination) {
        //destination.setDate(Instant.ofEpochSecond(source.getDate()));
    }

}

