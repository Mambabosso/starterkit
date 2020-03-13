package com.github.mambabosso.starterkit.dao;

import com.querydsl.jpa.impl.JPAQuery;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public abstract class GenericDAO<T extends Serializable> extends AbstractDAO<T> {

    private final SessionFactory sessionFactory;

    protected GenericDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    private Session session() {
        return currentSession();
    }

    protected final JPAQuery<T> query(final long offset, final long limit) {
        JPAQuery<T> query = new JPAQuery<>(session());
        if (offset > -1) {
            query = query.offset(offset);
        }
        if (limit > 0) {
            query = query.limit(limit);
        }
        return query;
    }

    protected final JPAQuery<T> query() {
        return query(-1, -1);
    }

    protected final Serializable create(final T value) {
        Objects.requireNonNull(value);
        return session().save(value);
    }

    protected final Optional<T> tryGet(final Serializable id) {
        Objects.requireNonNull(id);
        return Optional.ofNullable(session().get(getEntityClass(), id));
    }

    protected final SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
