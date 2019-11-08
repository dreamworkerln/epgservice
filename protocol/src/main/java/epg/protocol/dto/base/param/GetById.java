package epg.protocol.dto.base.param;

import epg.protocol.dto.base.jrpc.AbstractDto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class GetById extends AbstractDto {
    private Long id;

    protected GetById() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
