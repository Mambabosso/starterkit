package com.github.mambabosso.starterkit.user;

import com.github.mambabosso.starterkit.dao.GenericDAO;
import org.hibernate.SessionFactory;

import java.util.Optional;

public final class UserDAO extends GenericDAO<User> {

    private QUser user = QUser.user;

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User create(String name, String mail, String password) throws Exception {
        User user = User.create(name, password);
        user.setMail(mail);
        Long id = (Long)create(user);
        if (user.getId().equals(id)) {
            return user;
        }
        throw new Exception("user id does not equal created id");
    }

    public Optional<User> getUserByName(String name) {
        return Optional.ofNullable(query().select(user).from(user).where(user.name.eq(name)).fetchFirst());
    }

}
