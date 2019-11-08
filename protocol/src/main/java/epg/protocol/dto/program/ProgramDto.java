package epg.protocol.dto.program;

import epg.protocol.dto.base.jrpc.AbstractDto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProgramDto extends AbstractDto {

    //private String date;
    private Integer channelId; // channelId
    private Instant start;
    private Instant stop;
    private String title;
    private String description;
    private CreditsDto credits;

    private List<String> country = new ArrayList<>();
    private Integer dvbgenre; // hex-notation
    private Integer raiting;
    private List<IconDto> iconList = new ArrayList<>();


    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
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

    public CreditsDto getCredits() {
        return credits;
    }

    public void setCredits(CreditsDto credits) {
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

    public List<IconDto> getIconList() {
        return iconList;
    }

    public void setIconList(List<IconDto> iconList) {
        this.iconList = iconList;
    }
}
