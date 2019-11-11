package epg.protocol.dto.base.param;

import epg.protocol.dto.base.jrpc.AbstractDto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Scope("prototype")
public class GetByIntervalDto extends AbstractDto {

    private Long start;
    private Long end;

    protected GetByIntervalDto() {}

    public GetByIntervalDto(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
