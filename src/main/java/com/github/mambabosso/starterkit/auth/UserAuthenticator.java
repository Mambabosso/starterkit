package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.model.user.QUser;
import com.github.mambabosso.starterkit.model.user.User;
import com.querydsl.jpa.impl.JPAQuery;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.Optional;

public final class UserAuthenticator implements Authenticator<BasicCredentials, User> {

    private final QUser user = QUser.user;

    private final SessionFactory sessionFactory;

    public UserAuthenticator(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory);
    }

    private Optional<User> authenticate(String name, String plain_password) {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            try (Session session = sessionFactory.openSession()) {
                JPAQuery<User> query = new JPAQuery<>(session);
                User u = query.select(user).from(user).where(user.name.eq(name)).fetchFirst();
                if (u != null && BCrypt.checkpw(plain_password, u.getPassword())) {
                    return Optional.of(u);
                }
                return Optional.empty();
            } catch (Exception ex) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        return authenticate(basicCredentials.getUsername(), basicCredentials.getPassword());
    }

}
