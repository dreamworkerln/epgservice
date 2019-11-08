package epg.server.entities.program;

import java.util.ArrayList;
import java.util.List;

public class Credits {
    private List<String> director = new ArrayList<>();
    private List<String> presenter = new ArrayList<>();
    private List<String> actor = new ArrayList<>();

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
}
