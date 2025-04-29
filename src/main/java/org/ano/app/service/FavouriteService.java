package org.ano.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.ws.rs.WebApplicationException;
import org.ano.app.model.Favourite;
import org.ano.app.model.Film;
import org.ano.app.model.repository.FavouriteRepository;
import org.ano.app.model.repository.FilmRepository;
import org.ano.app.model.repository.UserRepository;

import java.util.List;

@ApplicationScoped
public class FavouriteService {
    @Inject
    FavouriteRepository favRepo;
    @Inject
    FilmRepository filmRepo;
    @Inject
    UserRepository userRepo;

    public void addFavourite(Long userId, Short filmId) {
        var user = userRepo.findById(userId);
        var film = filmRepo.getFilm(filmId)
                .orElseThrow(() -> new WebApplicationException("Film non trouvé", 404));
        if (favRepo.findByUserAndFilm(userId, filmId).isEmpty()) {
            Favourite f = new Favourite();
            f.setUser(user);
            f.setFilm(film);
            favRepo.persist(f);
        }
    }

    public void removeFavourite(Long userId, Short filmId) {
        favRepo.findByUserAndFilm(userId, filmId)
                .ifPresent(favRepo::delete);
    }

    public List<Film> listFavourites(Long userId) {
        return favRepo.findByUserId(userId).stream()
                .map(fav -> fav.getFilm()).toList();
    }
}

