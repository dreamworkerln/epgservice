package epg.protocol.dto.base.param;

import epg.protocol.dto.base.jrpc.AbstractDto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Scope("prototype")
public class GetByInterval extends AbstractDto {

    private Instant start;
    private Instant end;

    protected GetByInterval() {}

    public GetByInterval(Instant start, Instant end) {
        this.start = start;
        this.end = end;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }
}
