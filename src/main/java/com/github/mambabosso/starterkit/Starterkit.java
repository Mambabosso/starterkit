package com.github.mambabosso.starterkit;

import com.github.mambabosso.starterkit.health.DatabaseHealthCheck;
import io.dropwizard.Application;
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

    @Override
    public void initialize(final Bootstrap<StarterkitConfiguration> bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Override
    public void run(final StarterkitConfiguration starterkitConfiguration, final Environment environment) throws Exception {
        this.environment = environment;
        this.configuration = configuration;
        registerHealthChecks();
    }

    private void registerHealthChecks() {
        environment.healthChecks().register("DatabaseHealthCheck", new DatabaseHealthCheck());
    }

}
