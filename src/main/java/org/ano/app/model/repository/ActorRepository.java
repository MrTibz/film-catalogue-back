package org.ano.app.model.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ano.app.model.*;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ActorRepository {

    @Inject
    JPAStreamer jpaStreamer;

    public Optional<Actor> getActorById(short actorId) {
            return jpaStreamer.stream(Actor.class)
                    .filter(Actor$.actor_id.equal(actorId))
                    .findFirst();
        }
}