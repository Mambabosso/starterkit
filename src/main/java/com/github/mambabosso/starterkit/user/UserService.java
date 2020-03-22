package com.github.mambabosso.starterkit.user;

import com.github.mambabosso.starterkit.StarterkitConfiguration;
import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.util.Result;
import com.github.mambabosso.starterkit.util.Validator;

import java.util.Objects;
import java.util.Optional;

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
            if (!Validator.isValidName(name)) {
                return Result.failure(Errors.NAME_VALIDATION_FAIL);
            }
            if (mail == null || mail.trim().isEmpty()) {
                return Result.failure(Errors.INVALID_MAIL);
            }
            if (!Validator.isValidMail(mail)) {
                return Result.failure(Errors.MAIL_VALIDATION_FAIL);
            }
            if (password == null || password.trim().isEmpty()) {
                return Result.failure(Errors.INVALID_PASSWORD);
            }
            if (userDAO.getUserByName(name).isPresent()) {
                return Result.failure(Errors.NAME_ALREADY_TAKEN);
            }
            if (userDAO.getUserByMail(mail).isPresent()) {
                return Result.failure(Errors.MAIL_ALREADY_TAKEN);
            }
            return Result.success(userDAO.create(name, mail, password));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    public Result<User> getByName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return Result.failure(Errors.INVALID_NAME);
            }
            Optional<User> user = userDAO.getUserByName(name);
            return user.map(Result::success).orElseGet(() -> Result.failure(Errors.INVALID_NAME));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    public Result<User> getByMail(String mail) {
        try {
            if (mail == null || mail.trim().isEmpty()) {
                return Result.failure(Errors.INVALID_MAIL);
            }
            Optional<User> user = userDAO.getUserByMail(mail);
            return user.map(Result::success).orElseGet(() -> Result.failure(Errors.INVALID_MAIL));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
