package com.github.mambabosso.starterkit.resources;

import com.github.mambabosso.starterkit.user.User;
import com.github.mambabosso.starterkit.user.UserService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = Objects.requireNonNull(userService);
    }

    @GET
    @UnitOfWork
    @Path("/get")
    public Response get(@Auth User user) {
        return Response.status(400).entity(null).build();
    }

}
