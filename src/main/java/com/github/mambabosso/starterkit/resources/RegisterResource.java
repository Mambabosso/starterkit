package com.github.mambabosso.starterkit.resources;

import com.github.mambabosso.starterkit.user.User;
import com.github.mambabosso.starterkit.user.UserService;
import com.github.mambabosso.starterkit.util.Data;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.Optional;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegisterResource {

    private final UserService userService;

    public RegisterResource(UserService userService) {
        this.userService = Objects.requireNonNull(userService);
    }

    @POST
    @UnitOfWork
    @Path("/new")
    public Response register(Data data) {
        String name = data.tryGet("name");
        String password = data.tryGet("password");
        Optional<User> user = userService.register(name, password);
        if (user.isPresent()) {
            return Response.ok(user.get()).build();
        }
        return Response.status(400).build();
    }

    /*@GET
    @UnitOfWork
    @Path("/new/create")
    public Response register(@QueryParam("name") String name, @QueryParam("password") String password) {
        Optional<User> user = userService.register(name, password);
        if (user.isPresent()) {
            return Response.ok(user.get()).build();
        }
        return Response.status(400).build();
    }*/

}
