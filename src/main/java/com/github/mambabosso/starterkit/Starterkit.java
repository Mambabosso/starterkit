package com.github.mambabosso.starterkit;

import com.github.mambabosso.starterkit.auth.UserAuthenticator;
import com.github.mambabosso.starterkit.auth.UserAuthorizer;
import com.github.mambabosso.starterkit.health.DatabaseHealthCheck;
import com.github.mambabosso.starterkit.jwt.JWTConfiguration;
import com.github.mambabosso.starterkit.resources.AuthResource;
import com.github.mambabosso.starterkit.resources.RegisterResource;
import com.github.mambabosso.starterkit.model.role.Role;
import com.github.mambabosso.starterkit.model.role.RoleDAO;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.model.user.UserDAO;
import com.github.mambabosso.starterkit.service.AuthService;
import com.github.mambabosso.starterkit.service.RegisterService;
import com.github.mambabosso.starterkit.util.Helper;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
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
    private RegisterService registerService;
    private AuthService authService;

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
        createDAOServices();
        registerAuth();
        registerResources();
    }

    private void registerWebApp() {
        bootstrap.addBundle(new AssetsBundle("/webapp", "/", "index.html"));
    }

    private void registerHibernateBundle() {
        hibernateBundle = createHibernateBundle(User.class, Role.class);
        bootstrap.addBundle(hibernateBundle);
    }

    private void setUrlPattern() {
        environment.jersey().setUrlPattern("/api/*");
    }

    private void registerHealthChecks() {
        environment.healthChecks().register("database", new DatabaseHealthCheck(hibernateBundle.getSessionFactory()));
    }

    private void createDAOServices() {
        UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        RoleDAO roleDAO = new RoleDAO(hibernateBundle.getSessionFactory());
        registerService = new RegisterService(configuration, userDAO);
        authService = new AuthService(configuration, userDAO);
    }

    private void registerAuth() {

        OAuthCredentialAuthFilter.Builder<User> oauthBuilder = new OAuthCredentialAuthFilter.Builder<>();

        Class<?>[] classes = Helper.classArray(JWTConfiguration.class, UserDAO.class);
        Object[] params = Helper.objectArray(configuration.getJWTConfiguration(), authService.getUserDAO());
        oauthBuilder.setAuthenticator(new UnitOfWorkAwareProxyFactory(hibernateBundle).create(UserAuthenticator.class, classes, params));

        oauthBuilder.setAuthorizer(new UserAuthorizer());

        oauthBuilder.setPrefix("Bearer");

        environment.jersey().register(new AuthDynamicFeature(oauthBuilder.buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

    }

    private void registerResources() {
        environment.jersey().register(new AuthResource(authService));
        environment.jersey().register(new RegisterResource(registerService));
    }

}
