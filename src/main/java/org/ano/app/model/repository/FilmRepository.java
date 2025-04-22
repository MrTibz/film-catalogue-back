package org.ano.app.model.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.ano.app.dto.FilmDTO;
import org.ano.app.mapper.FilmMapper;
import org.ano.app.model.Category;
import org.ano.app.model.Film;
import org.ano.app.model.Film$;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ApplicationScoped
public class FilmRepository {

    @Inject
    JPAStreamer jpaStreamer;

    public static final int PAGE_SIZE = 20;

    public Optional<Film> getFilm(short filmId){
        return jpaStreamer.stream(Film.class)
                .filter(Film$.filmId.equal(filmId))
                .findFirst();
    }

    public Stream<Film> paged(long page, short minLength){
        return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length))
                .filter(Film$.length.greaterThan(minLength))
                .sorted(Film$.title)
                .skip(page*PAGE_SIZE)
                .limit(PAGE_SIZE);
    }

    public long countFilmsWithMinLength (short minLength){
        return jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(minLength))
                .count()/PAGE_SIZE;
    }

    public Stream<Film> actorsFromMovies (String startWith){
        final StreamConfiguration<Film> sc =
                StreamConfiguration.of(Film.class).joining(Film$.actors);
        return jpaStreamer.stream(sc)
                .filter(Film$.title.startsWith(startWith))
                .sorted(Film$.title.reversed());
    }

    public Stream<Film> getActorsFromFilm (short filmID){
        final StreamConfiguration<Film> sc =
                StreamConfiguration.of(Film.class).joining(Film$.actors);
        return jpaStreamer.stream(sc)
                .filter(Film$.filmId.equal(filmID))
                .sorted(Film$.title);
    }

    public Optional<Film> getFilmId(String title){
        return jpaStreamer.stream(Film.class)
                .filter(Film$.title.equal(title))
                .findFirst();
    }


    public Stream<FilmDTO> pagedCategory(long page, short categoryId){
        return jpaStreamer.stream(Film.class)
                .filter(f -> f.getCategory().getCategory_id() == categoryId)
                .sorted(Comparator.comparing(Film::getTitle))
                .skip(page*PAGE_SIZE)
                .limit(PAGE_SIZE)
                .map(FilmMapper::toDTO);
    }

    public long countFilmInCategory(short categoryId){
        return jpaStreamer.stream(Film.class)
                .filter(f -> Objects.equals(f.getCategory().getCategory_id(),categoryId))
                .count()/PAGE_SIZE;
    }


    public Stream<Film> getAllFilms() {
        return jpaStreamer.stream(Film.class);
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


    public Stream<Film> getFilmsByActorName(String actorName) {
        return jpaStreamer.stream(Film.class)
                .filter(f -> f.getActors()
                        .stream()
                        .anyMatch(a -> (a.getFirst_name() + " " + a.getLast_name())
                                .equalsIgnoreCase(actorName)));
    }
}
