package epg.server.es.importer.mapper;

import epg.server.entities.base.mapper.AbstractMapper;
import epg.server.entities.channel.Channel;
import epg.server.es.importer.dto.channel.ChannelESDto;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ChannelESMapper extends AbstractMapper<Channel, ChannelESDto> {

    ChannelESMapper() {
        super(Channel.class, ChannelESDto.class);
    }

    @PostConstruct
    protected void setupMapper() {

        //

        TypeMap<Channel, ChannelESDto> typeMap =

                mapper.createTypeMap(Channel.class, ChannelESDto.class).addMappings(
                        // назначаем маппинг полей (для final типов map полей нельзя указывать - не поддерживается блевотекой)
                        // соответственно отключаем маппинг для полей, которые не совпадают по типам
                        mapper -> {
                            //mapper.skip(OrderDto::setDate);
                            // такие типы (поля) конвертируются потом отдельно в пост-конвертере, конкретно тут - в mapSpecificFields
                        }).setPostConverter(getToDtoConverter());


        mapper.createTypeMap(ChannelESDto.class, Channel.class).addMappings(
                mapper -> {
                    mapper.skip(Channel::setName);
                }).setPostConverter(getToEntityConverter());


    }

    @Override
    protected void mapSpecificFields(Channel source, ChannelESDto destination) {
        //destination.setDate(source.getDate().getEpochSecond());
    }

    @Override
    protected void mapSpecificFields(ChannelESDto source, Channel destination) {

        destination.setName(source.getDisplayName().getContent());
    }

}


