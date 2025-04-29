package org.ano.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ano.app.dto.LoginRequest;
import org.ano.app.dto.SignupRequest;

import org.ano.app.model.User;

import org.ano.app.service.SessionService;
import org.ano.app.service.UserService;
import org.ano.app.security.AuthUtils;


import java.util.Map;


@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService userService;

    @Inject
    SessionService sessionService;

    @Inject
    AuthUtils authUtils;

    @POST
    @Path("/signup")
    public Response signup(SignupRequest request) {
        try {
            userService.register(request); // Toute la logique est déléguée
            return Response.status(Response.Status.CREATED).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        try {
            User user = userService.findByEmail(request.getEmail());

            boolean valid = authUtils.verifyPassword(
                    request.getPassword(),
                    user.getSalt(),
                    user.getPasswordHash());

            if (!valid) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Map.of("error", "Identifiants invalides"))
                        .build();
            }

            String sessionToken = authUtils.generateSessionToken(user.getEmail());
            sessionService.store(sessionToken, user.id);

            return Response.ok(Map.of(
                    "userId", user.id, // Panache public field
                    "email", user.getEmail(),
                    "session", sessionToken
            )).build();

        } catch (WebApplicationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", "Identifiants invalides"))
                    .build();
        }
    }

}
