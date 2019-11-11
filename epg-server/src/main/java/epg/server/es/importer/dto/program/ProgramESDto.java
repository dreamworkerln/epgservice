package epg.server.es.importer.dto.program;

import com.fasterxml.jackson.annotation.JsonProperty;
import epg.protocol.dto.base.jrpc.AbstractDto;

import java.util.ArrayList;
import java.util.List;

public class ProgramESDto extends AbstractDto {

    private TextLangESDto title;
    private TextLangESDto desc;
    private CreditsESDto credits;
    private String date;
    private List<TextLangESDto> country = new ArrayList<> ();
    private String dvbgenre;
    private RatingESDto rating;
    private List<IconESDto> iconList = new ArrayList<> ();
    private String start;
    private String stop;
    private Integer channel; // int, epg_api_id, channel_no


    // Getter Methods

    public TextLangESDto getTitle() {
        return title;
    }

    public TextLangESDto getDesc() {
        return desc;
    }

    public CreditsESDto getCredits() {
        return credits;
    }

    public String getDate() {
        return date;
    }

    public List<TextLangESDto> getCountry() {
        return country;
    }

    public String getDvbgenre() {
        return dvbgenre;
    }

    public RatingESDto getRating() {
        return rating;
    }

    public String getStart() {
        return start;
    }

    public String getStop() {
        return stop;
    }

    public Integer getChannel() {
        return channel;
    }

    public List<IconESDto> getIcon() {
        return iconList;
    }

    // Setter Methods

    public void setTitle(TextLangESDto title) {
        this.title = title;
    }

    public void setDesc(TextLangESDto lang) {
        this.desc = lang;
    }

    public void setCredits(CreditsESDto credits) {
        this.credits = credits;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCountry(List<TextLangESDto> country) {
        this.country = country;
    }

    public void setDvbgenre(String dvbgenre) {
        this.dvbgenre = dvbgenre;
    }

    public void setRating(RatingESDto ratingESDto) {
        this.rating = ratingESDto;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public void setIcon(List<IconESDto> iconESDtoList) {
        this.iconList = iconESDtoList;
    }

    @Override
    public String toString() {
        return "ProgramESDto{" +
               "title=" + title +
               ", desc=" + desc +
               ", credits=" + credits +
               ", date='" + date + '\'' +
               ", country=" + country +
               ", dvbgenre='" + dvbgenre + '\'' +
               ", rating=" + rating +
               ", iconList=" + iconList +
               ", start='" + start + '\'' +
               ", stop='" + stop + '\'' +
               ", channel='" + channel + '\'' +
               '}';
    }
}
