package org.ano.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ano.app.dto.ActorDTO;
import org.ano.app.dto.FilmDTO;
import org.ano.app.dto.FilmLiteDTO;
import org.ano.app.mapper.FilmMapper;
import org.ano.app.model.Film;
import org.ano.app.model.repository.FilmRepository;
import org.ano.app.service.FilmService;

import java.util.*;
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
    @Path("/movie/{filmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilmById(@PathParam("filmId") short filmId) {
        Optional<Film> filmOpt = filmRepository.getFilmWithActorsAndCategory(filmId);  // This method eagerly loads actors and category
        return filmOpt.map(film -> Response.ok(FilmMapper.toDTO(film)).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Film not found"))
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response actors(String startsWith) {
        List<FilmDTO> films = filmRepository.actorsFromMovies(startsWith)
                .map(FilmMapper::toDTO)
                .collect(Collectors.toList());

        // Vérifie si aucun film n’a été trouvé
        if (films.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Film non trouvé commençant par : " + startsWith ))
                    .build();
        }

        return Response.ok(films).build();

    }

    @GET
    @Path("/film/id/{filmId}/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorsFromFilm (short filmId){
        // Récupère les films correspondant à l'ID
        List<Film> films = filmRepository.getActorsFromFilm(filmId).collect(Collectors.toList());

        // Vérifie si aucun film n’a été trouvé
        if (films.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Film non trouvé pour l'ID : " + filmId))
                    .build();
        }

        List<ActorDTO> actors = filmRepository.getActorsFromFilm(filmId)
                .flatMap(f -> f.getActors().stream()
                        .map(a -> new ActorDTO(a.getFirst_name(), a.getLast_name())))
                .collect(Collectors.toList());

        return Response.ok(actors).build();
    }

    @GET
    @Path("/film/title/{title}/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorsFromTitle(String title) {
        Optional<Film> film = filmRepository.getFilmId(title);
        short filmId = -1;
        if (film.isPresent()) {
            filmId = film.get().getFilmId();
        }

        if (filmId == -1) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Film non trouvé.\"}")
                    .build();
        }

        List<ActorDTO> actors = filmRepository.getActorsFromFilm(filmId)
                .flatMap(f -> f.getActors().stream()
                        .map(a -> new ActorDTO(a.getFirst_name(), a.getLast_name())))
                .collect(Collectors.toList());

        return Response.ok(actors).build();
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
