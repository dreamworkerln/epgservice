package epg.protocol.dto.channel;

import epg.protocol.dto.program.ProgramDto;
import epg.protocol.dto.base.jrpc.AbstractDto;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

public class ChannelDto extends AbstractDto {

    private Integer id;

    private String name;

    private Map<Long,ProgramDto> programList = new TreeMap<>();

    protected ChannelDto() {}

    public ChannelDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, ProgramDto> getProgramList() {
        return programList;
    }

    public void setProgramList(Map<Long, ProgramDto> programList) {
        this.programList = programList;
        // set program owner
        programList.values().forEach(p -> p.setChannelId(this.id));
    }

}
