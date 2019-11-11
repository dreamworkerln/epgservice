package epg.protocol.dto.program;

import epg.protocol.dto.base.jrpc.AbstractDto;

import java.util.ArrayList;
import java.util.List;

public class ProgramDto extends AbstractDto {

    //private String date;
    private Integer channelId; // channelId
    private Long start;
    private Long stop;
    private String title;
    private String description;
    private CreditsDto credits;

    private List<String> country = new ArrayList<>();
    private Integer dvbgenre; // hex-notation
    private Integer rating;
    private List<IconDto> iconList = new ArrayList<>();


    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getStop() {
        return stop;
    }

    public void setStop(Long stop) {
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<IconDto> getIconList() {
        return iconList;
    }

    public void setIconList(List<IconDto> iconList) {
        this.iconList = iconList;
    }
}
