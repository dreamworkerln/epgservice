package epg.server.es.importer.dto.program;

public class IconESDto {

    private String src;
    private String position;
    private String type;

    public IconESDto() {}

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IconESDto{" +
               "src='" + src + '\'' +
               ", position='" + position + '\'' +
               ", type='" + type + '\'' +
               '}';
    }
}
