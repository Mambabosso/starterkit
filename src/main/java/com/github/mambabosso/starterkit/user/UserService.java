package com.github.mambabosso.starterkit.user;

import com.github.mambabosso.starterkit.StarterkitConfiguration;
import com.github.mambabosso.starterkit.util.Result;

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
                return Result.failure("name can not be null or empty");
            }
            if (mail != null && !mail.contains("@")) {
                return Result.failure("mail does not contain @");
            }
            if (password == null || password.trim().isEmpty()) {
                return Result.failure("password can not be null or empty");
            }
            if (userDAO.getUserByName(name).isPresent()) {
                return Result.failure("name is already taken");
            }
            return Result.success(userDAO.create(name, mail, password));
        } catch (Exception ex) {
            return Result.failure(ex.getMessage());
        }
    }

}
