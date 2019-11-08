package epg.server.entities.base.mapper;

import epg.protocol.dto.base.jrpc.AbstractDto;
import epg.server.entities.base.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);
}
