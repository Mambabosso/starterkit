package com.github.mambabosso.starterkit.service;

import com.github.mambabosso.starterkit.StarterkitConfiguration;
import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.model.token.Token;
import com.github.mambabosso.starterkit.model.token.TokenDAO;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.model.user.UserDAO;
import com.github.mambabosso.starterkit.util.Result;
import io.dropwizard.auth.Auth;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.Optional;

@Data
public final class AuthService {

    private final StarterkitConfiguration configuration;
    private final UserDAO userDAO;
    private final TokenDAO tokenDAO;

    public AuthService(StarterkitConfiguration configuration, UserDAO userDAO, TokenDAO tokenDAO) {
        this.configuration = Objects.requireNonNull(configuration);
        this.userDAO = Objects.requireNonNull(userDAO);
        this.tokenDAO = Objects.requireNonNull(tokenDAO);
    }

    public Result<User> getUserByCredentials(String name, String password) {
        try {
            Optional<User> user = userDAO.getUserByName(name);
            if (user.isPresent()) {
                User u = user.get();
                if (BCrypt.checkpw(password, u.getPassword())) {
                    return Result.success(u);
                }
            }
            return Result.failure(Errors.INVALID_CREDENTIALS);
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    public Result<Token> login(String name, String password) {
        try {
            Result<User> user = getUserByCredentials(name, password);
            if (user.isSuccess()) {
                Token token = tokenDAO.create(60 * 24 * 30, user.getValue());
                return Result.success(token);
            }
            return Result.failure(user.getError());
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    public Result<Long> logout(User user) {
        try {
            return Result.success(tokenDAO.updateExpired(user.getId()));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
