package com.github.mambabosso.starterkit.dao;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
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

    protected Session session() {
        return currentSession();
    }

    protected final HibernateQuery<T> query(final long offset, final long limit) {
        HibernateQuery<T> query = new HibernateQuery<>(session());
        if (offset > -1) {
            query = query.offset(offset);
        }
        if (limit > 0) {
            query = query.limit(limit);
        }
        return query;
    }

    protected final HibernateQuery<T> query() {
        return query(-1, -1);
    }

    protected final HibernateUpdateClause update(EntityPath<T> entityPath) {
        Objects.requireNonNull(entityPath);
        return new HibernateUpdateClause(session(), entityPath);
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
