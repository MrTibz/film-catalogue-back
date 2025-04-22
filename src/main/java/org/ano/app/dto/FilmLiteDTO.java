package org.ano.app.dto;

public class FilmLiteDTO {
    private Short filmId;
    private String title;

    public FilmLiteDTO() {}

    public FilmLiteDTO(Short filmId, String title) {
        this.filmId = filmId;
        this.title = title;
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
}
