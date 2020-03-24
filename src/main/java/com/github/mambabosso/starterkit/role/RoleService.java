package com.github.mambabosso.starterkit.role;

import com.github.mambabosso.starterkit.StarterkitConfiguration;
import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.util.Result;

import java.util.Objects;
import java.util.Optional;

public final class RoleService {

    private final StarterkitConfiguration configuration;
    private final RoleDAO roleDAO;

    public RoleService(StarterkitConfiguration configuration, RoleDAO roleDAO) {
        this.configuration = Objects.requireNonNull(configuration);
        this.roleDAO = Objects.requireNonNull(roleDAO);
    }

    public Result<Role> getByName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return Result.failure(Errors.INVALID_NAME);
            }
            Optional<Role> role = roleDAO.getRoleByName(name);
            return role.map(Result::success).orElseGet(() -> Result.failure(Errors.INVALID_NAME));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
