package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.model.user.User;
import io.dropwizard.auth.Authorizer;

import javax.annotation.Nullable;
import javax.ws.rs.container.ContainerRequestContext;

public final class UserAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role, @Nullable ContainerRequestContext requestContext) {
        return user.getRoles().stream().anyMatch(r -> r.getName().contentEquals(role));
    }

    @Override
    public boolean authorize(User user, String role) {
        return authorize(user, role, null);
    }

}
