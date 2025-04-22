package org.ano.app.mapper;

import org.ano.app.dto.FilmDTO;
import org.ano.app.model.Film;

public class FilmMapper {

    public static FilmDTO toDTO(Film film) {
        return new FilmDTO(
                film.getFilmId(),
                film.getTitle(),
                film.getCategory(),
                film.getDescription(),
                film.getRelease_year(),
                film.getLength(),
                film.getRental_rate()
        );
    }
}