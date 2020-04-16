package com.github.mambabosso.starterkit.service;

import com.github.mambabosso.starterkit.StarterkitConfiguration;
import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.jwt.JWTConfiguration;
import com.github.mambabosso.starterkit.jwt.JWTHandler;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.model.user.UserDAO;
import com.github.mambabosso.starterkit.util.Result;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.Optional;

@Data
public final class AuthService {

    private final StarterkitConfiguration configuration;
    private final JWTConfiguration jwtConfiguration;
    private final UserDAO userDAO;

    public AuthService(StarterkitConfiguration configuration, UserDAO userDAO) {
        this.configuration = Objects.requireNonNull(configuration);
        this.jwtConfiguration = Objects.requireNonNull(this.configuration.getJWTConfiguration());
        this.userDAO = Objects.requireNonNull(userDAO);
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

    public Result<String> login(String name, String password) {
        try {
            Result<User> user = getUserByCredentials(name, password);
            if (user.isSuccess()) {
                return JWTHandler.encode(jwtConfiguration, user.getValue().getName());
            }
            return Result.failure(user.getError());
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
