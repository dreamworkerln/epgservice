package epg.server.es.importer.dto.program;

public class RatingESDto {
    private String value;
    private String system;


    // Getter Methods

    public String getValue() {
        return value;
    }

    public String getSystem() {
        return system;
    }

    // Setter Methods

    public void setValue(String value) {
        this.value = value;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "RatingESDto{" +
               "value='" + value + '\'' +
               ", system='" + system + '\'' +
               '}';
    }
}
