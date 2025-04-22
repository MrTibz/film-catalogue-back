package org.ano.app.mapper;

import org.ano.app.dto.ActorDTO;
import org.ano.app.model.Actor;

public class ActorMapper {

    private ActorDTO toDTO(Actor actor) {
        return new ActorDTO(
                actor.getFirst_name(),
                actor.getLast_name()
        );
    }

}

