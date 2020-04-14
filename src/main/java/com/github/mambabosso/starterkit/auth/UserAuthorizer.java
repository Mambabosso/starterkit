package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.model.user.UserService;
import io.dropwizard.auth.Authorizer;

import javax.annotation.Nullable;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Objects;

public final class UserAuthorizer implements Authorizer<User> {

    private final UserService userService;

    public UserAuthorizer(UserService userService) {
        this.userService = Objects.requireNonNull(userService);
    }

    @Override
    public boolean authorize(User user, String role, @Nullable ContainerRequestContext requestContext) {
        return user.getRoles().stream().anyMatch(r -> r.getName().contentEquals(role));
    }

    @Override
    public boolean authorize(User user, String role) {
        return authorize(user, role, null);
    }

}
