package epg.server.entities.program;

import epg.server.entities.channel.Channel;
import epg.server.entities.base.AbstractEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class Program extends AbstractEntity {

    //private String date;
    private Channel channel; // channel owner
    private Instant start;
    private Instant stop;
    private String title;
    private String description;
    private Credits credits;

    private List<String> country = new ArrayList<>();
    private Integer dvbgenre; // hex-notation
    private Integer raiting;
    private List<Icon> iconList = new ArrayList<>();


    // =======================================================

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getStop() {
        return stop;
    }

    public void setStop(Instant stop) {
        this.stop = stop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public Integer getDvbgenre() {
        return dvbgenre;
    }

    public void setDvbgenre(Integer dvbgenre) {
        this.dvbgenre = dvbgenre;
    }

    public Integer getRaiting() {
        return raiting;
    }

    public void setRaiting(Integer raiting) {
        this.raiting = raiting;
    }

    public List<Icon> getIconList() {
        return iconList;
    }

    public void setIconList(List<Icon> iconList) {
        this.iconList = iconList;
    }
}


    // ========================================================




