package org.ano.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ano.app.dto.ActorDTO;
import org.ano.app.dto.CategoryDTO;
import org.ano.app.dto.FilmDTO;
import org.ano.app.mapper.ActorMapper;
import org.ano.app.mapper.FilmMapper;
import org.ano.app.model.repository.ActorRepository;
import org.ano.app.service.CategoryService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActorResource {

    @Inject
    ActorRepository actorRepository;

    @GET
    @Path("/actor/{actorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorByID(@PathParam("actorId") short actorID) {
        return actorRepository.getActorById(actorID)
                .map(a -> Response.ok(new ActorDTO(a.getFirst_name(), a.getLast_name())).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Acteur non trouvé"))
                        .build());
    }
}

