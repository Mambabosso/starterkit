package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.user.User;
import io.dropwizard.auth.Authorizer;

import javax.annotation.Nullable;
import javax.ws.rs.container.ContainerRequestContext;

public final class UserAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String roleLevel, @Nullable ContainerRequestContext requestContext) {
        return AuthHelper.authorize(user, roleLevel, requestContext);
    }

    @Override
    public boolean authorize(User user, String roleLevel) {
        return authorize(user, roleLevel, null);
    }

}
