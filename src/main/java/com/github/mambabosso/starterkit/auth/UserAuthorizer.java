package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.user.User;
import com.github.mambabosso.starterkit.util.Helper;
import io.dropwizard.auth.Authorizer;
import org.hibernate.SessionFactory;

import javax.annotation.Nullable;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Objects;

public final class UserAuthorizer implements Authorizer<User> {

    private final SessionFactory sessionFactory;

    public UserAuthorizer(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory);
    }

    @Override
    public boolean authorize(User user, String roleLevel, @Nullable ContainerRequestContext requestContext) {
        int level = user.getRoleLevel();
        int neededLevel = Helper.toInteger(roleLevel);
        return neededLevel > 0 && level >= neededLevel;
    }

    @Override
    public boolean authorize(User user, String roleLevel) {
        return authorize(user, roleLevel, null);
    }

}
