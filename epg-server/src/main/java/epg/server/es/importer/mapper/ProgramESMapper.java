package epg.server.es.importer.mapper;

import epg.server.entities.base.mapper.AbstractMapper;
import epg.server.entities.channel.Channel;
import epg.server.entities.program.Icon;
import epg.server.entities.program.IconType;
import epg.server.entities.program.Program;
import epg.server.entities.programguide.ProgramGuide;
import epg.server.es.importer.dto.program.IconESDto;
import epg.server.es.importer.dto.program.ProgramESDto;
import epg.server.es.importer.dto.program.TextLangESDto;
import epg.server.repository.ProgramGuideRepository;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProgramESMapper extends AbstractMapper<Program, ProgramESDto> {

    private final ProgramGuideRepository programGuideRepository;

    @Autowired
    ProgramESMapper(ProgramGuideRepository programGuideRepository) {
        super(Program.class, ProgramESDto.class);
        this.programGuideRepository = programGuideRepository;
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
                    mapper.skip(Program::setChannel);
                    mapper.skip(Program::setDvbgenre);
                    mapper.skip(Program::setTitle);
                    mapper.skip(Program::setDescription);
                    mapper.skip(Program::setRaiting);
                    mapper.skip(Program::setCountry);
                    mapper.skip(Program::setIconList);
                    mapper.skip(Program::setStart);
                    mapper.skip(Program::setStop);
                    mapper.skip(Program::setIconList);
                }).setPostConverter(getToEntityConverter());


    }

    @Override
    protected void mapSpecificFields(Program source, ProgramESDto destination) {
        //destination.setDate(source.getDate().getEpochSecond());
    }

    @Override
    protected void mapSpecificFields(ProgramESDto source, Program destination) {

        final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss Z");

        ProgramGuide progGuide = programGuideRepository.get();

        // channel
        Channel channel = progGuide.getChannelList().get(source.getChannel());
        Assert.notNull(channel, "Channel " + source.getChannel() + " is null");
        destination.setChannel(channel);

        // dvbgenre
        destination.setDvbgenre(Integer.decode(source.getDvbgenre()));

        // title
        if (Objects.nonNull(source.getTitle())) {
            destination.setTitle(source.getTitle().getContent());
        }

        // description
        if (Objects.nonNull(source.getDesc())) {
            destination.setDescription(source.getDesc().getContent());
        }
        
        // raiting
        if (Objects.nonNull(source.getRating())) {
            destination.setRaiting(Integer.parseInt(source.getRating().getValue()));
        }

        // country
        destination.setCountry(source.getCountry().stream().map(TextLangESDto::getContent).collect(Collectors.toList()));

        // start
        destination.setStart(DATE_TIME_FORMATTER.parse(source.getStart(),Instant::from));

        // stop
        destination.setStop(DATE_TIME_FORMATTER.parse(source.getStop(),Instant::from));

        // icon
        destination.setIconList(source.getIcon().stream()
                .map(dd -> new Icon(IconType.valueOf(dd.getType().toUpperCase()), dd.getSrc())).collect(Collectors.toList()));
    }

}

