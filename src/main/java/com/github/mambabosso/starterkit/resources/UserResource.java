package com.github.mambabosso.starterkit.resources;

import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.model.user.UserService;
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

    @GET
    @RolesAllowed("5")
    @UnitOfWork
    @Path("/find")
    public Response find(@Auth User user, @QueryParam("offset") long offset, @QueryParam("limit") long limit, @QueryParam("name") String name, @QueryParam("mail") String mail) {
        if (name != null && mail != null) {
            return Response.status(400).build();
        }
        Result<List<User>> list = Result.failure(Errors.UNKNOWN);
        if (name != null) {
            list = userService.findByName(offset, limit, name);
        }
        if (mail != null) {
            list = userService.findByMail(offset, limit, mail);
        }
        if (list.isSuccess()) {
            return Response.ok(list).build();
        }
        return Response.status(400).entity(list).build();
    }

}
