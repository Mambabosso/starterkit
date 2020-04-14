package com.github.mambabosso.starterkit.model.role;

import com.github.mambabosso.starterkit.dao.GenericDAO;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class RoleDAO extends GenericDAO<Role> {

    private final QRole role = QRole.role;

    public RoleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Role create(String name, int level) throws Exception {
        Role role = Role.create(name, level);
        Long id = (Long)create(role);
        if (role.getId().equals(id)) {
            return role;
        }
        throw new Exception();
    }

    public Optional<Role> getRoleByName(String name) {
        return Optional.ofNullable(query().select(role).from(role).where(role.name.eq(name)).fetchFirst());
    }

}
