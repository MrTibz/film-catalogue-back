package org.ano.app.mapper;

import org.ano.app.dto.ActorDTO;
import org.ano.app.dto.CategoryDTO;
import org.ano.app.dto.FilmDTO;
import org.ano.app.model.Category;
import org.ano.app.model.Film;

import java.util.List;
import java.util.stream.Collectors;

public class FilmMapper {

    public static FilmDTO toDTO(Film film) {
        List<ActorDTO> actorDTOs = film.getActors().stream()
                .map(a -> new ActorDTO(a.getFirst_name(), a.getLast_name()))
                .collect(Collectors.toList());

        Category category = film.getCategory();
        CategoryDTO categoryDTO = null;
        if (category != null) {
            categoryDTO = new CategoryDTO(category.getCategory_id(), category.getName());
        }
        return new FilmDTO(

                film.getFilmId(),
                film.getTitle(),
                categoryDTO,
                film.getDescription(),
                film.getRelease_year(),
                film.getLength(),
                film.getRental_rate(),
                actorDTOs // liste d'actors transformée
        );
    }
}
