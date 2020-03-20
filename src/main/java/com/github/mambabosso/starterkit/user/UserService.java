package com.github.mambabosso.starterkit.user;

import com.github.mambabosso.starterkit.StarterkitConfiguration;
import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.util.Helper;
import com.github.mambabosso.starterkit.util.Result;

import java.util.Objects;

public final class UserService {

    private final StarterkitConfiguration configuration;
    private final UserDAO userDAO;

    public UserService(StarterkitConfiguration configuration, UserDAO userDAO) {
        this.configuration = Objects.requireNonNull(configuration);
        this.userDAO = Objects.requireNonNull(userDAO);
    }

    public Result<User> register(String name, String mail, String password) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return Result.failure(Errors.INVALID_NAME);
            }
            if (Helper.containsWhitespace(name)) {
                return Result.failure(Errors.NAME_CONTAINS_WHITESPACE);
            }
            if (mail != null && !Helper.isValidMail(mail)) {
                return Result.failure(Errors.MAIL_VALIDATION_FAIL);
            }
            if (password == null || password.trim().isEmpty()) {
                return Result.failure(Errors.INVALID_PASSWORD);
            }
            if (userDAO.getUserByName(name).isPresent()) {
                return Result.failure(Errors.NAME_ALREADY_TAKEN);
            }
            return Result.success(userDAO.create(name, mail, password));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
