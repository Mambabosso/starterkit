package com.github.mambabosso.starterkit.model.user;

import com.github.mambabosso.starterkit.StarterkitConfiguration;
import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.util.Result;
import com.github.mambabosso.starterkit.util.Validator;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
public final class UserService {

    private final StarterkitConfiguration configuration;
    private final UserDAO userDAO;

    public UserService(StarterkitConfiguration configuration, UserDAO userDAO) {
        this.configuration = Objects.requireNonNull(configuration);
        this.userDAO = Objects.requireNonNull(userDAO);
    }

    public Result<User> register(String name, String mail, String password) {
        try {
            if (name == null || name.trim().isEmpty() || !Validator.isValidName(name)) {
                return Result.failure(Errors.NAME_VALIDATION_FAIL);
            }
            if (mail == null || mail.trim().isEmpty() || !Validator.isValidMail(mail)) {
                return Result.failure(Errors.MAIL_VALIDATION_FAIL);
            }
            if (password == null || password.trim().isEmpty() || !Validator.isValidPassword(password)) {
                return Result.failure(Errors.PASSWORD_VALIDATION_FAIL);
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

    public Result<User> getByCredentials(String name, String password) {
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

    public Result<List<User>> getAll(long offset, long limit) {
        try {
            return Result.success(userDAO.getAll(offset, limit));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    public Result<List<User>> findByName(long offset, long limit, String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return Result.failure(Errors.INVALID_NAME);
            }
            return Result.success(userDAO.findByName(offset, limit, name));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    public Result<List<User>> findByMail(long offset, long limit, String mail) {
        try {
            if (mail == null || mail.trim().isEmpty()) {
                return Result.failure(Errors.INVALID_MAIL);
            }
            return Result.success(userDAO.findByMail(offset, limit, mail));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
