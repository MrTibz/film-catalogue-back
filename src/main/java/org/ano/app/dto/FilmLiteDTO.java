package org.ano.app.dto;

public class FilmLiteDTO {
    private Short filmId;
    private String title;
    private String description ;

    public FilmLiteDTO() {}

    public FilmLiteDTO(Short filmId, String title, String description) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
    }

    public Short getFilmId() {
        return filmId;
    }

    public void setFilmId(Short filmId) {
        this.filmId = filmId;
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
}
