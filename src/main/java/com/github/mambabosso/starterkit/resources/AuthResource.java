package com.github.mambabosso.starterkit.resources;

import com.github.mambabosso.starterkit.model.token.Token;
import com.github.mambabosso.starterkit.model.token.TokenService;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.model.user.UserService;
import com.github.mambabosso.starterkit.util.DataMap;
import com.github.mambabosso.starterkit.util.Result;
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

    private final UserService userService;
    private final TokenService tokenService;

    public AuthResource(UserService userService, TokenService tokenService) {
        this.userService = Objects.requireNonNull(userService);
        this.tokenService = Objects.requireNonNull(tokenService);
    }

    @POST
    @UnitOfWork
    @Path("/login")
    public Response login(DataMap dataMap) {
        String name = dataMap.tryGet("username", String.class);
        String password = dataMap.tryGet("password", String.class);
        Result<User> user = userService.getByCredentials(name, password);
        if (user.isSuccess()) {
            Result<Token> token = tokenService.create(60 * 24 * 30, user.getValue());
            if (token.isSuccess()) {
                return Response.status(200).entity(token).build();
            }
        }
        return Response.status(400).entity(user).build();
    }

}
