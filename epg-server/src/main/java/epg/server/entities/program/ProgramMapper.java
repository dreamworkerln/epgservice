package epg.server.entities.program;

import epg.protocol.dto.program.IconDto;
import epg.protocol.dto.program.ProgramDto;
import epg.server.es.importer.dto.program.ProgramESDto;
import epg.server.entities.base.mapper.AbstractMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProgramMapper extends AbstractMapper<Program, ProgramDto> {

    ProgramMapper() {
        super(Program.class, ProgramDto.class);
    }

    @PostConstruct
    protected void setupMapper() {

        TypeMap<Program, ProgramDto> tmEtD =

                mapper.createTypeMap(Program.class, ProgramDto.class).addMappings(
                        // назначаем маппинг полей (для final типов map полей нельзя указывать - не поддерживается блевотекой)
                        // соответственно отключаем маппинг для полей, которые не совпадают по типам
                        mapper -> {
                            mapper.skip(ProgramDto::setStart);
                            mapper.skip(ProgramDto::setStop);
                            mapper.skip(ProgramDto::setIconList);
                        }).setPostConverter(getToDtoConverter());



        TypeMap<ProgramDto, Program> tmDtE = 
        mapper.createTypeMap(ProgramDto.class, Program.class).addMappings(
                mapper -> {

                }).setPostConverter(getToEntityConverter());

    }

    @Override
    protected void mapSpecificFields(Program source, ProgramDto destination) {
        //destination.setDate(source.getDate().getEpochSecond());

        destination.setStart(source.getStart().getEpochSecond());
        destination.setStop(source.getStop().getEpochSecond());

        source.getIconList().forEach(ic -> {
            destination.getIconList().add(new IconDto(ic.getType().toString().toLowerCase(), ic.getUrl()));
        });

    }

    @Override
    protected void mapSpecificFields(ProgramDto  source, Program destination) {
        //destination.setDate(Instant.ofEpochSecond(source.getDate()));
    }


}