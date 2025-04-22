package org.ano.app.dto;

import org.ano.app.model.Category;

import java.math.BigDecimal;
import java.util.List;

public class FilmDTO {
    private List<ActorDTO> actors ;
    private Category category;
    private Short filmId;
    private String title;
    private String description;
    private Short releaseYear;
    private Short length;
    private BigDecimal rental_rate;

    // Constructeurs, getters et setters
    public FilmDTO() {}

    public FilmDTO(Short filmId, String title, Category category, String description, Short releaseYear, Short length, BigDecimal rental_rate,List<ActorDTO> actors) {
        this.filmId = filmId;
        this.title = title;
        this.category = category;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
        this.rental_rate = rental_rate;
        this.actors = actors;
    }

    public List<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(List<ActorDTO> actors) {
        this.actors = actors;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Short releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public BigDecimal getRental_rate() {
        return rental_rate;
    }

    public void setRental_rate(BigDecimal rental_rate) {
        this.rental_rate = rental_rate;
    }
}
