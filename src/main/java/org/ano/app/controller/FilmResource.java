package org.ano.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ano.app.dto.FilmDTO;
import org.ano.app.dto.FilmLiteDTO;
import org.ano.app.mapper.FilmMapper;
import org.ano.app.model.Film;
import org.ano.app.model.repository.FilmRepository;
import org.ano.app.service.FilmService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.ano.app.model.repository.FilmRepository.PAGE_SIZE;


@Path("/v1")
public class FilmResource {

    @Inject
    FilmRepository filmRepository;

    @Inject
    FilmService filmService;

    @GET
    @Path("/film/{filmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilmById(@PathParam("filmId") short filmId) {
        return filmRepository.getFilm(filmId)
                .map(film -> {
                    FilmDTO dto = FilmMapper.toDTO(film);
                    return Response.ok(dto).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Film non trouvé"))
                        .build());
    }

    @GET
    @Path("/pagedFilms/{page}/{minLength}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> pagedFilmsJSON(@PathParam("page") long page, @PathParam("minLength") short minLength) {
        List<FilmDTO> films = filmRepository.paged(page, minLength)
                .map(f -> FilmMapper.toDTO(f))
                .collect(Collectors.toList());

        long totalPages = filmRepository.countFilmsWithMinLength(minLength);

        return Map.of(
                "page", page,
                "totalPages", totalPages,
                "films", films
        );
    }

    @GET
    @Path("/actors/{startsWith}")
    @Produces(MediaType.TEXT_PLAIN)
    public String actors(String startsWith) {
        return filmRepository.actorsFromMovies(startsWith)
                .map(f -> String.format("%s \t (%d min): \t %s",
                        f.getTitle(),
                        f.getLength(),
                        f.getActors().stream()
                                .map(a -> String.format("%s %s",
                                        a.getFirst_name(),
                                        a.getLast_name()))
                                .collect(Collectors.joining(", "))))
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("/film/id/{filmId}/actors")
    @Produces(MediaType.TEXT_PLAIN)
    public String getActorsFromFilm (short filmId){
        return filmRepository.getActorsFromFilm(filmId)
                .map(f -> String.format("%s : %s",
                        f.getTitle(),
                        f.getActors().stream()
                                .map(a-> String.format("%s %s",
                                        a.getFirst_name(),
                                        a.getLast_name()))
                                .collect(Collectors.joining(", "))))
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("/film/title/{title}/actors")
    @Produces(MediaType.TEXT_PLAIN)
    public String getActorsFromTitle (String title){
        Optional<Film> film = filmRepository.getFilmId(title);
        short filmId = -1;
        if (film.isPresent()) {
            filmId = film.get().getFilmId();
        }
        return filmId == -1 ? "Film non trouvé." : filmRepository.getActorsFromFilm(filmId)
                .map(f -> String.format("%s : %s",
                        f.getTitle(),
                        f.getActors().stream()
                                .map(a-> String.format("%s %s",
                                        a.getFirst_name(),
                                        a.getLast_name()))
                                .collect(Collectors.joining(", "))))
                .collect(Collectors.joining("\n"));
    }


    @GET
    @Path("/category/{page}/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> pagedCategoryJSON(int page, short categoryId) {
        // Récupérer le nom de la catégorie une seule fois
        String categoryName = filmRepository.pagedCategory(page, categoryId)
                .findFirst()
                .map(f -> f.getCategoryDTO().getName())
                .orElse("Unknown Category");

        // Utiliser AtomicInteger pour la numérotation
        AtomicInteger counter = new AtomicInteger((page - 1) * PAGE_SIZE + 1);

        // Créer une liste de films avec leurs informations détaillées
        List<Map<String, Object>> films = filmRepository.pagedCategory(page, categoryId)
                .map(f -> {
                    Map<String, Object> film = new HashMap<>();
                    film.put("id", f.getFilmId());  // Assurez-vous que vous avez un identifiant unique
                    film.put("title", f.getTitle());
                    film.put("description", f.getDescription());  // Description du film
                    //film.put("poster", f.getPosterUrl());  // Si vous avez une URL d'affiche
                    return film;
                })
                .collect(Collectors.toList());

        // Construire l'objet JSON
        Map<String, Object> responseJSON = Map.of(
                "page", page,
                "totalPages", filmRepository.countFilmInCategory(categoryId),
                "category", categoryName,
                "films", films
        );

        return responseJSON;
    }

    @GET
    @Path("/actor/{actorId}/films")
    public List<FilmLiteDTO> getFilmsByActor(short actorId) {
        return filmService.getFilmsByActorIdLiteDTO(actorId);
    }

    @GET
    @Path("/actor/name/{name}/films")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FilmDTO> getFilmsByActorName(String name) {
        return filmService.getFilmsByActorName(name);
    }
}
