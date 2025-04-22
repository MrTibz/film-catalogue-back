package org.ano.app.model;


import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "category", schema = "sakila")
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_id")
    private short category_id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_update")
    private Timestamp last_update;

    @OneToMany(mappedBy = "category")
    private List<Film> films;

    public short getCategory_id() {
        return category_id;
    }

    public void setCategory_id(short category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
