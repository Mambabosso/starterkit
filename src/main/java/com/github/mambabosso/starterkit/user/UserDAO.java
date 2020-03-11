package com.github.mambabosso.starterkit.user;

import com.github.mambabosso.starterkit.dao.GenericDAO;
import org.hibernate.SessionFactory;

public final class UserDAO extends GenericDAO<User> {

    private QUser user = QUser.user;

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
