package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.user.QUser;
import com.github.mambabosso.starterkit.user.User;
import com.github.mambabosso.starterkit.util.Helper;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.Nullable;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Objects;
import java.util.Optional;

public final class AuthHelper {

    private static SessionFactory sessionFactory;

    public static void setSessionFactory(SessionFactory sf) {
        sessionFactory = Objects.requireNonNull(sf);
    }

    private static final QUser user = QUser.user;

    private static Optional<User> getUser(String name, String plain_password) {
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

    public static Optional<User> authenticate(String name, String plain_password) {
        return getUser(name, plain_password);
    }

    public static boolean authorize(User user, String roleLevel, @Nullable ContainerRequestContext requestContext) {
        int level = Helper.toInteger(user.getRoleLevel());
        int neededLevel = Helper.toInteger(roleLevel);
        return neededLevel > 0 && level >= neededLevel;
    }

}
