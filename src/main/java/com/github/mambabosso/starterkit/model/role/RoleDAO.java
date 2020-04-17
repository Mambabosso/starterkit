package com.github.mambabosso.starterkit.model.role;

import com.github.mambabosso.starterkit.dao.GenericDAO;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class RoleDAO extends GenericDAO<Role> {

    private final QRole role = QRole.role;

    public RoleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Long create(Role role) {
        return (Long)save(role);
    }

    public Optional<Role> getRoleByName(String name) {
        return Optional.ofNullable(query().select(role).from(role).where(role.name.eq(name)).fetchFirst());
    }

}
