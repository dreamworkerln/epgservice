package epg.server.entities.program;

public class Icon {
    private IconType type;
    private String url;

    protected Icon() {}

    public Icon(IconType type, String url) {
        this.type = type;
        this.url = url;
    }

    public IconType getType() {
        return type;
    }

    public void setType(IconType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
