package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.StarterkitConfiguration;
import com.github.mambabosso.starterkit.jwt.JWTConfiguration;
import com.github.mambabosso.starterkit.jwt.JWTHandler;
import com.github.mambabosso.starterkit.model.user.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Objects;
import java.util.Optional;

public class UserAuthenticator implements Authenticator<String, User> {

    private final JWTConfiguration jwtConfiguration;

    public UserAuthenticator(StarterkitConfiguration configuration) {
        this.jwtConfiguration = Objects.requireNonNull(configuration.getJWTConfiguration());
    }

    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        return JWTHandler.decode(jwtConfiguration, token).optional();
    }

}
