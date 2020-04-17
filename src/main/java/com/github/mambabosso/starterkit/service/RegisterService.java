package com.github.mambabosso.starterkit.service;

import com.github.mambabosso.starterkit.StarterkitConfiguration;
import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.model.user.UserDAO;
import com.github.mambabosso.starterkit.util.Result;
import com.github.mambabosso.starterkit.util.Validator;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

@Data
public final class RegisterService {

    private final StarterkitConfiguration configuration;
    private final UserDAO userDAO;

    public RegisterService(StarterkitConfiguration configuration, UserDAO userDAO) {
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
            User user = new User();
            user.setName(name);
            user.setMail(mail);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(14)));
            if (userDAO.create(user) > 0) {
                return Result.success(user);
            }
            return Result.failure(Errors.UNKNOWN);
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
