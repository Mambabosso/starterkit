package com.github.mambabosso.starterkit.resources;

import com.github.mambabosso.starterkit.user.User;
import com.github.mambabosso.starterkit.user.UserService;
import com.github.mambabosso.starterkit.util.DataMap;
import com.github.mambabosso.starterkit.util.Result;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

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
    public Response register(DataMap dataMap) {
        String name = dataMap.tryGet("username", String.class);
        String mail = dataMap.tryGet("email", String.class);
        String password = dataMap.tryGet("password", String.class);
        Result<User> user = userService.register(name, mail, password);
        if (user.isSuccess()) {
            return Response.ok(user).build();
        }
        return Response.status(400).entity(user).build();
    }

    /*@GET
    @UnitOfWork
    @Path("/new/create")
    public Response register(@QueryParam("name") String name, @QueryParam("mail") String mail, @QueryParam("password") String password) {
        Result<User> user = userService.register(name, mail, password);
        if (user.isSuccess()) {
            return Response.ok(user).build();
        }
        return Response.status(400).entity(user).build();
    }*/

}
