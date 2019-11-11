package epg.server.entities.base.param;

import epg.server.entities.base.AbstractEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Scope("prototype")
public class GetByInterval extends AbstractEntity {

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

    public static void validate(GetByInterval request) {

        if (request == null) {
            throw new IllegalArgumentException("Error parsing request: params == null");
        }

        if (request.getStart() == null ||
            request.getEnd() == null) {
            throw new IllegalArgumentException("Error parsing request: start/end == null");
        }
    }
}



