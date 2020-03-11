package com.github.mambabosso.starterkit;

import com.github.mambabosso.starterkit.health.DatabaseHealthCheck;
import com.github.mambabosso.starterkit.resources.RegisterResource;
import com.github.mambabosso.starterkit.user.User;
import com.github.mambabosso.starterkit.user.UserDAO;
import com.github.mambabosso.starterkit.user.UserService;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public final class Starterkit extends Application<StarterkitConfiguration> {

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            new Starterkit().run(args);
        } else {
            throw new IllegalArgumentException("param args");
        }
    }

    private Bootstrap<StarterkitConfiguration> bootstrap;
    private Environment environment;
    private StarterkitConfiguration configuration;

    private HibernateBundle<StarterkitConfiguration> hibernateBundle;
    private UserService userService;

    private HibernateBundle<StarterkitConfiguration> createHibernateBundle(Class<?> c, Class<?> ...classes) {
        return new HibernateBundle<StarterkitConfiguration>(c, classes) {
            @Override
            public DataSourceFactory getDataSourceFactory(StarterkitConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    @Override
    public void initialize(final Bootstrap<StarterkitConfiguration> bootstrap) {
        this.bootstrap = bootstrap;
        registerHibernateBundle();
    }

    @Override
    public void run(final StarterkitConfiguration starterkitConfiguration, final Environment environment) throws Exception {
        this.environment = environment;
        this.configuration = starterkitConfiguration;
        setUrlPattern();
        registerHealthChecks();
        createDAOServices();
        registerResources();
    }

    private void registerHibernateBundle() {
        hibernateBundle = createHibernateBundle(User.class);
        bootstrap.addBundle(hibernateBundle);
    }

    private void setUrlPattern() {
        environment.jersey().setUrlPattern("/api/*");
    }

    private void registerHealthChecks() {
        environment.healthChecks().register("DatabaseHealthCheck", new DatabaseHealthCheck());
    }

    private void createDAOServices() {
        userService = new UserService(configuration, new UserDAO(hibernateBundle.getSessionFactory()));
    }

    private void registerResources() {
        environment.jersey().register(new RegisterResource(userService));
    }

}