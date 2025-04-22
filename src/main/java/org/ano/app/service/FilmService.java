package org.ano.app.service;
import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.ano.app.dto.FilmDTO;
import org.ano.app.dto.FilmLiteDTO;
import org.ano.app.model.Film;
import org.ano.app.model.repository.FilmRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.ano.app.mapper.FilmMapper;


@ApplicationScoped
public class FilmService {

    @Inject
    FilmRepository filmRepository;

    public List<FilmDTO> getAllFilms() {
        return filmRepository.getAllFilms()
                .map(FilmMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PersistenceContext
    EntityManager em;

    public List<Film> getFilmsByActorId(short actorId) {
        return em.createQuery("""
        SELECT f FROM Film f
        JOIN f.actors a
        WHERE a.actor_id = :actorId
    """, Film.class)
                .setParameter("actorId", actorId)
                .getResultList();
    }

    public List<FilmDTO> getFilmsByActorIdDTO(short actorId) {
        List<Film> films = getFilmsByActorId(actorId);
        return films.stream()
                .map(FilmMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FilmLiteDTO> getFilmsByActorIdLiteDTO(short actorId) {
        List<Film> films = getFilmsByActorId(actorId);
        return films.stream()
                .map(film -> new FilmLiteDTO(film.getFilmId(), film.getTitle(), film.getDescription()))
                .collect(Collectors.toList());
    }


    public List<FilmDTO> getFilmsByActorName(String name) {
        return filmRepository.getFilmsByActorName(name)
                .map(FilmMapper::toDTO)
                .collect(Collectors.toList());
    }
}

