package org.ano.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.ano.app.dto.FilmDTO;
import org.ano.app.model.Film;
import org.ano.app.service.FavouriteService;
import org.ano.app.service.SessionService;

import java.util.List;

@Path("/favourites")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FavouriteResource {

    @Inject
    FavouriteService service;

    @Inject
    SessionService sessionService;

    private Long getUserId(HttpHeaders headers) {
        String token = headers.getHeaderString("X-Token");
        if (token == null || sessionService.getUserIdFromToken(token) == null) {
            throw new WebApplicationException("Non authentifié", Response.Status.UNAUTHORIZED);
        }
        System.out.println("Token reçu : " + token);
        return sessionService.getUserIdFromToken(token);
    }

    @GET
    public List<FilmDTO> list(@Context HttpHeaders headers) {
        Long userId = getUserId(headers);
        return service.listFavourites(userId);
    }

    @POST
    @Path("/{filmId}")
    public void add(@PathParam("filmId") Short filmId, @Context HttpHeaders headers) {
        Long userId = getUserId(headers);
        service.addFavourite(userId, filmId);
    }

    @DELETE
    @Path("/{filmId}")
    public void remove(@PathParam("filmId") Short filmId, @Context HttpHeaders headers) {
        Long userId = getUserId(headers);
        service.removeFavourite(userId, filmId);
    }
}
