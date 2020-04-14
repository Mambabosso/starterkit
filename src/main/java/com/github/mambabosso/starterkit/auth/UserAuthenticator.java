package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.model.user.UserService;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Objects;
import java.util.Optional;

public final class UserAuthenticator implements Authenticator<String, User> {

    private final UserService userService;

    public UserAuthenticator(UserService userService) {
        this.userService = Objects.requireNonNull(userService);
    }

    @Override
    public Optional<User> authenticate(String authToken) throws AuthenticationException {
        return Optional.empty();
    }

}
