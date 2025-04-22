package org.ano.app.service;
import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ano.app.dto.FilmDTO;
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

    public List<FilmDTO> getFilmsByActorId(short actorId) {
        return filmRepository.getFilmsByActorId(actorId)
                .stream()
                .map(FilmMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<FilmDTO> getFilmsByActorName(String name) {
        return filmRepository.getFilmsByActorName(name)
                .map(FilmMapper::toDTO)
                .collect(Collectors.toList());
    }
}