package com.github.mambabosso.starterkit.resources;

import com.github.mambabosso.starterkit.user.User;
import com.github.mambabosso.starterkit.user.UserService;
import com.github.mambabosso.starterkit.util.Result;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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
    @RolesAllowed("5")
    @UnitOfWork
    @Path("/get")
    public Response get(@Auth User user, @QueryParam("offset") long offset, @QueryParam("limit") long limit) {
        Result<List<User>> list = userService.getAll(offset, limit);
        if (list.isSuccess()) {
            return Response.ok(list).build();
        }
        return Response.status(400).entity(list).build();
    }

}
