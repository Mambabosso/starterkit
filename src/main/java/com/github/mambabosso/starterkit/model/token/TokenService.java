package com.github.mambabosso.starterkit.model.token;

import com.github.mambabosso.starterkit.StarterkitConfiguration;
import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.util.Result;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;

@Data
public final class TokenService {

    private final StarterkitConfiguration configuration;
    private final TokenDAO tokenDAO;

    public TokenService(StarterkitConfiguration configuration, TokenDAO tokenDAO) {
        this.configuration = Objects.requireNonNull(configuration);
        this.tokenDAO = Objects.requireNonNull(tokenDAO);
    }

    public Result<Token> getByValue(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return Result.failure(Errors.INVALID_TOKEN);
            }
            Optional<Token> token = tokenDAO.getByValue(value);
            return token.map(Result::success).orElseGet(() -> Result.failure(Errors.INVALID_TOKEN));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    public Result<Token> create(int lifetimeMinutes, User owner) {
        try {
            if (owner == null) {
                return Result.failure(Errors.INVALID_USER);
            }
            return Result.success(tokenDAO.create(lifetimeMinutes, owner));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
