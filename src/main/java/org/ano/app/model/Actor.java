package org.ano.app.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "actor", schema = "sakila")
public class Actor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "actor_id")
    private short actor_id;
    @Basic
    @Column(name = "first_name")
    private String first_name;
    @Basic
    @Column(name = "last_name")
    private String last_name;
    @Basic
    @Column(name = "last_update")
    private Timestamp last_update;
    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    private Set<Film> films = new HashSet<>();


    public short getActor_id() {return actor_id;}

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setActor_id(short actor_id) {
        this.actor_id = actor_id;
    }
}
