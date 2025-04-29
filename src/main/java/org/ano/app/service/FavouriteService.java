package org.ano.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.ano.app.dto.ActorDTO;
import org.ano.app.dto.CategoryDTO;
import org.ano.app.dto.FilmDTO;
import org.ano.app.dto.FilmLiteDTO;
import org.ano.app.model.Favourite;
import org.ano.app.model.Film;
import org.ano.app.model.repository.FavouriteRepository;
import org.ano.app.model.repository.FilmRepository;
import org.ano.app.model.repository.UserRepository;


import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FavouriteService {
    @Inject
    FavouriteRepository favRepo;
    @Inject
    FilmRepository filmRepo;
    @Inject
    UserRepository userRepo;


    @Transactional
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

    @Transactional
    public void removeFavourite(Long userId, Short filmId) {
        favRepo.findByUserAndFilm(userId, filmId)
                .ifPresent(favRepo::delete);
    }

    public List<FilmDTO> listFavourites(Long userId) {
        return favRepo.findByUserId(userId).stream()
                .map(fav -> convertToFilmDTO(fav.getFilm()))
                .collect(Collectors.toList());
    }


    private FilmDTO convertToFilmDTO(Film film) {
        // Construction d'un FilmDTO avec des données supplémentaires si nécessaire
        CategoryDTO categoryDTO = new CategoryDTO(film.getCategory().getCategory_id(), film.getCategory().getName());
        List<ActorDTO> actorDTOs = film.getActors().stream()
                .map(actor -> new ActorDTO(actor.getFirst_name(), actor.getLast_name()))
                .collect(Collectors.toList());

        return new FilmDTO(film.getFilmId(), film.getTitle(), categoryDTO, film.getDescription(), film.getRelease_year(), film.getLength(), film.getRental_rate(), actorDTOs);
    }
}

