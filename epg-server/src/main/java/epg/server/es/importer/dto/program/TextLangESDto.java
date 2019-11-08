package epg.server.es.importer.dto.program;

public class TextLangESDto {
    private String lang;
    private String content;


    // Getter Methods

    public String getLang() {
        return lang;
    }

    public String getContent() {
        return content;
    }

    // Setter Methods

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Description{" +
               "lang='" + lang + '\'' +
               ", content='" + content + '\'' +
               '}';
    }
}
