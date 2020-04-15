package com.github.mambabosso.starterkit.resources;

import com.github.mambabosso.starterkit.model.token.Token;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.service.AuthService;
import com.github.mambabosso.starterkit.util.DataMap;
import com.github.mambabosso.starterkit.util.Result;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = Objects.requireNonNull(authService);
    }

    @POST
    @UnitOfWork
    @Path("/login")
    public Response login(DataMap dataMap) {
        String name = dataMap.tryGet("username", String.class);
        String password = dataMap.tryGet("password", String.class);
        Result<Token> token = authService.login(name, password);
        if (token.isSuccess()) {
            return Response.status(200).entity(token).build();
        }
        return Response.status(400).entity(token).build();
    }

    @POST
    @UnitOfWork
    @Path("/logout")
    public Response logout(@Auth User user) {
        Result<Token> token = authService.logout(user);
        if (token.isSuccess()) {
            return Response.status(200).entity(token).build();
        }
        return Response.status(400).entity(token).build();
    }

}
