package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.jwt.Handler;
import com.github.mambabosso.starterkit.jwt.JWTConfiguration;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.model.user.UserDAO;
import com.github.mambabosso.starterkit.util.Result;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Objects;
import java.util.Optional;

public class UserAuthenticator implements Authenticator<String, User> {

    private final JWTConfiguration jwtConfiguration;
    private final UserDAO userDAO;

    public UserAuthenticator(JWTConfiguration jwtConfiguration, UserDAO userDAO) {
        this.jwtConfiguration = Objects.requireNonNull(jwtConfiguration);
        this.userDAO = Objects.requireNonNull(userDAO);
    }

    private Result<User> get(String token) {
        try {
            Result<Long> userId = Handler.decode(jwtConfiguration, token);
            if (userId.isSuccess()) {
                Optional<User> user = userDAO.getUserById(userId.getValue());
                if (user.isPresent()) {
                    return Result.success(user.get());
                }
            }
            return Result.failure(Errors.INVALID_TOKEN);
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    @UnitOfWork
    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        return get(token).optional();
    }

}
