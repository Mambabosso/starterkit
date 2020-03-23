package com.github.mambabosso.starterkit.health;

import com.codahale.metrics.health.HealthCheck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Objects;

public final class DatabaseHealthCheck extends HealthCheck {

    private final SessionFactory sessionFactory;

    public DatabaseHealthCheck(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory);
    }

    private Result databaseAvailable() {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            try (Session session = sessionFactory.openSession()) {
                if (session.createNativeQuery("select 1 from dual;").getSingleResult() != null) {
                    return Result.healthy();
                }
                return Result.unhealthy("Database not available");
            } catch (Exception ex) {
                return Result.unhealthy(ex);
            }
        }
        return Result.unhealthy("Database not available");
    }

    @Override
    protected Result check() throws Exception {
        return databaseAvailable();
    }

}
