package org.ano.app.dto;

import java.util.List;

public class FilmDTObis {
    public String title;
    public List<ActorDTO> actors;

    public FilmDTObis(String title, List<ActorDTO> actors) {
        this.title = title;
        this.actors = actors;
    }
}