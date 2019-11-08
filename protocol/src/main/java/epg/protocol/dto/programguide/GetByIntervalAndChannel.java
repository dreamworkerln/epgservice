package epg.protocol.dto.programguide;

import epg.protocol.dto.base.jrpc.AbstractDto;

import java.time.Instant;

public class GetByIntervalAndChannel extends AbstractDto {

    private Instant start;
    private Instant end;
    private Integer channelId;

    protected GetByIntervalAndChannel() {}

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

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
}
