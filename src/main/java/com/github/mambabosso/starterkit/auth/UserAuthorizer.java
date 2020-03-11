package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.user.User;
import io.dropwizard.auth.Authorizer;

import javax.annotation.Nullable;
import javax.ws.rs.container.ContainerRequestContext;

public final class UserAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role, @Nullable ContainerRequestContext requestContext) {
        return AuthHelper.authorize(user, role, requestContext);
    }

    @Override
    public boolean authorize(User user, String role) {
        return authorize(user, role, null);
    }

}
