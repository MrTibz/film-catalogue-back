package org.ano.app.model.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ano.app.model.Favourite;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FavouriteRepository implements PanacheRepository<Favourite> {
    public List<Favourite> findByUserId(Long userId) {
        return list("user.id", userId);
    }

    public Optional<Favourite> findByUserAndFilm(Long userId, Short filmId) {
        return find("user.id = ?1 and film.id = ?2", userId, filmId).firstResultOptional();
    }
}

