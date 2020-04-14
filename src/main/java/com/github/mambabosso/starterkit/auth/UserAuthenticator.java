package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.model.token.Token;
import com.github.mambabosso.starterkit.model.token.TokenDAO;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.util.Result;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Objects;
import java.util.Optional;

public class UserAuthenticator implements Authenticator<String, User> {

    private final TokenDAO tokenDAO;

    public UserAuthenticator(TokenDAO tokenDAO) {
        this.tokenDAO = Objects.requireNonNull(tokenDAO);
    }

    private Result<User> get(String authToken) {
        try {
            Optional<Token> token = tokenDAO.getByValue(authToken);
            if (token.isPresent()) {
                Token t = token.get();
                if (!t.expired()) {
                    return Result.success(t.getOwner());
                }
                return Result.failure(Errors.TOKEN_EXPIRED);
            }
            return Result.failure(Errors.UNKNOWN);
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    @UnitOfWork
    @Override
    public Optional<User> authenticate(String authToken) throws AuthenticationException {
        return get(authToken).optional();
    }

}
