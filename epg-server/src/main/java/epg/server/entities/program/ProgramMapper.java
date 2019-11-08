package epg.server.entities.program;

import epg.server.es.importer.dto.program.ProgramESDto;
import epg.server.entities.base.mapper.AbstractMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProgramMapper extends AbstractMapper<Program, ProgramESDto> {

    ProgramMapper() {
        super(Program.class, ProgramESDto.class);
    }

    @PostConstruct
    protected void setupMapper() {

        TypeMap<Program, ProgramESDto> typeMap =

                mapper.createTypeMap(Program.class, ProgramESDto.class).addMappings(
                        // назначаем маппинг полей (для final типов map полей нельзя указывать - не поддерживается блевотекой)
                        // соответственно отключаем маппинг для полей, которые не совпадают по типам
                        mapper -> {
                            //mapper.skip(OrderDto::setDate);
                            // такие типы (поля) конвертируются потом отдельно в пост-конвертере, конкретно тут - в mapSpecificFields
                        }).setPostConverter(getToDtoConverter());




        mapper.createTypeMap(ProgramESDto.class, Program.class).addMappings(
                mapper -> {
                    //mapper.skip(Order::setDate);
                }).setPostConverter(getToEntityConverter());




    }

    @Override
    protected void mapSpecificFields(Program source, ProgramESDto destination) {
        //destination.setDate(source.getDate().getEpochSecond());
    }

    @Override
    protected void mapSpecificFields(ProgramESDto  source, Program destination) {
        //destination.setDate(Instant.ofEpochSecond(source.getDate()));
    }


}