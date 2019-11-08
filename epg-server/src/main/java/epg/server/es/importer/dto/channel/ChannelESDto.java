package epg.server.es.importer.dto.channel;


import com.fasterxml.jackson.annotation.JsonProperty;
import epg.protocol.dto.base.jrpc.AbstractDto;
import epg.server.es.importer.dto.program.TextLangESDto;


public class ChannelESDto extends AbstractDto {

    private TextLangESDto displayName;

    private Icon icon;
    private Integer id;


    // Getter Methods

    @JsonProperty("display-name")
    public TextLangESDto getDisplayName() {
        return displayName;
    }

    public Icon getIcon() {
        return icon;
    }

    public float getId() {
        return id;
    }

    // Setter Methods

    @JsonProperty("display-name")
    public void setDisplayName(TextLangESDto displayName) {
        this.displayName = displayName;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

