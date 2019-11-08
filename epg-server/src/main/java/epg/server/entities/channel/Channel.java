package epg.server.entities.channel;

import epg.server.entities.base.AbstractEntity;
import epg.server.entities.program.Program;

import java.time.Instant;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;


public class Channel extends AbstractEntity {

    private Integer id;

    private String name;

    private ConcurrentNavigableMap<Instant,Program> programList = new ConcurrentSkipListMap<>();

    protected Channel() {}

    public Channel(Integer id, String name) {
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

    public ConcurrentNavigableMap<Instant, Program> getProgramList() {
        return programList;
    }

    public void setProgramList(ConcurrentNavigableMap<Instant, Program> programList) {
        this.programList = programList;
        // set program owner
        programList.values().forEach(p -> p.setChannel(this));
    }

    @Override
    public String toString() {
        return "Channel{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
