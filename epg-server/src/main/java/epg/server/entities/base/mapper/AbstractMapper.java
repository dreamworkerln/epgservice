package epg.server.entities.base.mapper;

import epg.protocol.dto.base.jrpc.AbstractDto;
import epg.server.entities.base.AbstractEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


// ModelMapper: путешествие туда и обратно
// https://habr.com/ru/post/438808/
//
// https://github.com/promoscow/modelmapper-demo
public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> implements Mapper<E, D> {


    protected ModelMapper mapper;

    private Class<E> entityClass;
    private Class<D> dtoClass;

    // Не нужно предупреждать, это абстрактный класс
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public final void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    protected AbstractMapper(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, entityClass);
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, dtoClass);
    }

    protected Converter<E, D> getToDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected Converter<D, E> getToEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }



    protected void mapSpecificFields(E source, D destination) {}

    protected void mapSpecificFields(D source, E destination) {}

}
