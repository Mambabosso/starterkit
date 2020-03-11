package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.user.User;

import javax.annotation.Nullable;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Optional;

public final class AuthHelper {

    public static Optional<User> authenticate(String name, String password) {
        return Optional.empty();
    }

    public static boolean authorize(User user, String role, @Nullable ContainerRequestContext requestContext) {
        return false;
    }

}
