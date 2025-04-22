package org.ano.app.dto;

import org.ano.app.mapper.FilmMapper;
import org.ano.app.model.Category;
import org.ano.app.model.Film;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class FilmDTO {
    private List<ActorDTO> actors ;
    private CategoryDTO categoryDTO;
    private Short filmId;
    private String title;
    private String description;
    private Short releaseYear;
    private Short length;
    private BigDecimal rental_rate;

    // Constructeurs, getters et setters
    public FilmDTO() {}

    public FilmDTO(Short filmId, String title, CategoryDTO categoryDTO, String description, Short releaseYear, Short length, BigDecimal rental_rate,List<ActorDTO> actors) {
        this.filmId = filmId;
        this.title = title;
        this.categoryDTO = categoryDTO;
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

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
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
