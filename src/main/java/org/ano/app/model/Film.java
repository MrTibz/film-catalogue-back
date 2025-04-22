package org.ano.app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film", schema = "sakila")
public class Film {

    public Film(){}

    public Film(short filmId, String title, short length, String description){
        this.filmId = filmId;
        this.title = title;
        this.length = length;
        this.description = description;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "film_id")
    private short filmId;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "release_year", columnDefinition = "YEAR")
    private Short release_year;
    @Basic
    @Column(name = "language_id")
    private short language_id;
    @Basic
    @Column(name = "original_language_id")
    private Short original_language_id;
    @Basic
    @Column(name = "rental_duration")
    private short rental_duration;
    @Basic
    @Column(name = "rental_rate")
    private BigDecimal rental_rate;
    @Basic
    @Column(name = "length")
    private Short length;
    @Basic
    @Column(name = "replacement_cost")
    private BigDecimal replacement_cost;
    @Basic
    @Column(name = "rating", columnDefinition = "enum('G','PG','PG-13','R','NC-17')")
    private Object rating;
    @Basic
    @Column(name = "special_features", columnDefinition = "set('Trailers','Commentaries','Deleted Scenes','Behind the Scenes')")
    private Object special_features;
    @Basic
    @Column(name = "last_update")
    private Timestamp last_update;

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    @JsonIgnore  // Ajouté pour éviter la boucle infinie
    private List<Actor> actors = new ArrayList<>();

    @ManyToOne
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Category category;




    public short getFilmId() {
        return filmId;
    }

    public void setFilmId(short filmId) {
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

    public Short getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Short release_year) {
        this.release_year = release_year;
    }

    public short getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(short language_id) {
        this.language_id = language_id;
    }

    public Short getOriginal_language_id() {
        return original_language_id;
    }

    public void setOriginal_language_id(Short original_language_id) {
        this.original_language_id = original_language_id;
    }

    public short getRental_duration() {
        return rental_duration;
    }

    public void setRental_duration(short rental_duration) {
        this.rental_duration = rental_duration;
    }

    public BigDecimal getRental_rate() {
        return rental_rate;
    }

    public void setRental_rate(BigDecimal rental_rate) {
        this.rental_rate = rental_rate;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public BigDecimal getReplacement_cost() {
        return replacement_cost;
    }

    public void setReplacement_cost(BigDecimal replacement_cost) {
        this.replacement_cost = replacement_cost;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public Object getSpecial_features() {
        return special_features;
    }

    public void setSpecial_features(Object special_features) {
        this.special_features = special_features;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
