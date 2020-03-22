package com.github.mambabosso.starterkit;

import com.github.mambabosso.starterkit.auth.AuthHelper;
import com.github.mambabosso.starterkit.auth.UserAuthenticator;
import com.github.mambabosso.starterkit.auth.UserAuthorizer;
import com.github.mambabosso.starterkit.health.DatabaseHealthCheck;
import com.github.mambabosso.starterkit.resources.RegisterResource;
import com.github.mambabosso.starterkit.user.User;
import com.github.mambabosso.starterkit.user.UserDAO;
import com.github.mambabosso.starterkit.user.UserService;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

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
        registerWebApp();
        registerHibernateBundle();
    }

    @Override
    public void run(final StarterkitConfiguration starterkitConfiguration, final Environment environment) throws Exception {
        this.environment = environment;
        this.configuration = starterkitConfiguration;
        setUrlPattern();
        registerHealthChecks();
        registerAuth();
        createDAOServices();
        registerResources();
    }

    private void registerWebApp() {
        bootstrap.addBundle(new AssetsBundle("/webapp", "/", "index.html"));
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

    private void registerAuth() {
        AuthHelper.setSessionFactory(hibernateBundle.getSessionFactory());
        BasicCredentialAuthFilter.Builder<User> builder = new BasicCredentialAuthFilter.Builder<>();
        builder.setAuthenticator(new UserAuthenticator());
        builder.setAuthorizer(new UserAuthorizer());
        builder.setRealm("Starterkit Realm");
        environment.jersey().register(new AuthDynamicFeature(builder.buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    private void createDAOServices() {
        userService = new UserService(configuration, new UserDAO(hibernateBundle.getSessionFactory()));
    }

    private void registerResources() {
        environment.jersey().register(new RegisterResource(userService));
    }

}
