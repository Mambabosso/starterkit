package com.github.mambabosso.starterkit.user;

import com.github.mambabosso.starterkit.StarterkitConfiguration;

import java.util.Objects;
import java.util.Optional;

public final class UserService {

    private final StarterkitConfiguration configuration;
    private final UserDAO userDAO;

    public UserService(StarterkitConfiguration configuration, UserDAO userDAO) {
        this.configuration = Objects.requireNonNull(configuration);
        this.userDAO = Objects.requireNonNull(userDAO);
    }

    public Optional<User> register(String name, String password) {
        return Optional.empty();
    }

}
