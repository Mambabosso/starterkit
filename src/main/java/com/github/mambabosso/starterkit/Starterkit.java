package com.github.mambabosso.starterkit;

import com.github.mambabosso.starterkit.auth.UserAuthenticator;
import com.github.mambabosso.starterkit.auth.UserAuthorizer;
import com.github.mambabosso.starterkit.health.DatabaseHealthCheck;
import com.github.mambabosso.starterkit.model.token.Token;
import com.github.mambabosso.starterkit.model.token.TokenDAO;
import com.github.mambabosso.starterkit.model.token.TokenService;
import com.github.mambabosso.starterkit.resources.AuthResource;
import com.github.mambabosso.starterkit.resources.RegisterResource;
import com.github.mambabosso.starterkit.resources.UserResource;
import com.github.mambabosso.starterkit.model.role.Role;
import com.github.mambabosso.starterkit.model.role.RoleDAO;
import com.github.mambabosso.starterkit.model.role.RoleService;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.model.user.UserDAO;
import com.github.mambabosso.starterkit.model.user.UserService;
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
    private UserService userService;
    private RoleService roleService;
    private TokenService tokenService;

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
        hibernateBundle = createHibernateBundle(User.class, Role.class, Token.class);
        bootstrap.addBundle(hibernateBundle);
    }

    private void setUrlPattern() {
        environment.jersey().setUrlPattern("/api/*");
    }

    private void registerHealthChecks() {
        environment.healthChecks().register("database", new DatabaseHealthCheck(hibernateBundle.getSessionFactory()));
    }

    private void createDAOServices() {
        userService = new UserService(configuration, new UserDAO(hibernateBundle.getSessionFactory()));
        roleService = new RoleService(configuration, new RoleDAO(hibernateBundle.getSessionFactory()));
        tokenService = new TokenService(configuration, new TokenDAO(hibernateBundle.getSessionFactory()));
    }

    private void registerAuth() {
        OAuthCredentialAuthFilter.Builder<User> builder = new OAuthCredentialAuthFilter.Builder<>();
        builder.setAuthenticator(new UnitOfWorkAwareProxyFactory(hibernateBundle).create(UserAuthenticator.class, TokenDAO.class, tokenService.getTokenDAO()));
        builder.setAuthorizer(new UserAuthorizer());
        builder.setPrefix("Bearer");
        environment.jersey().register(new AuthDynamicFeature(builder.buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    private void registerResources() {
        environment.jersey().register(new AuthResource(userService, tokenService));
        environment.jersey().register(new RegisterResource(userService));
        environment.jersey().register(new UserResource(userService));
    }

}
