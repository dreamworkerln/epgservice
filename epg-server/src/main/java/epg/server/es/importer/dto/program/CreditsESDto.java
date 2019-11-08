package epg.server.es.importer.dto.program;

import java.util.ArrayList;
import java.util.List;

public class CreditsESDto {
    List<String> director = new ArrayList<>();
    List<String> presenter = new ArrayList<>();
    List<String> actor = new ArrayList<>();

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public List<String> getPresenter() {
        return presenter;
    }

    public void setPresenter(List<String> presenter) {
        this.presenter = presenter;
    }

    public List<String> getActor() {
        return actor;
    }

    public void setActor(List<String> actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "CreditsESDto{" +
               "director=" + director +
               ", presenter=" + presenter +
               ", actor=" + actor +
               '}';
    }
}
