package com.github.mambabosso.starterkit.model.user;

import com.github.mambabosso.starterkit.dao.GenericDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class UserDAO extends GenericDAO<User> {

    private final QUser user = QUser.user;

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public String create(User user) {
        return (String)save(user);
    }

    public Optional<User> getUserByName(String name) {
        return Optional.ofNullable(query().select(user).from(user).where(user.name.eq(name)).fetchFirst());
    }

    public Optional<User> getUserByMail(String mail) {
        return Optional.ofNullable(query().select(user).from(user).where(user.mail.eq(mail)).fetchFirst());
    }

    public List<User> getAll(long offset, long limit) {
        return query(offset, limit).select(user).from(user).fetch();
    }

    public List<User> findByName(long offset, long limit, String name) {
        return query(offset, limit).select(user).from(user).where(user.name.containsIgnoreCase(name)).fetch();
    }

    public List<User> findByMail(long offset, long limit, String mail) {
        return query(offset, limit).select(user).from(user).where(user.mail.containsIgnoreCase(mail)).fetch();
    }

}
