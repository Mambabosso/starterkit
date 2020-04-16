package com.github.mambabosso.starterkit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mambabosso.starterkit.jwt.JWTConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class StarterkitConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    private JWTConfiguration jwtConfiguration = new JWTConfiguration();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }

    @JsonProperty("jwtConfiguration")
    public JWTConfiguration getJWTConfiguration() {
        return jwtConfiguration;
    }

    @JsonProperty("jwtConfiguration")
    public void setJWTConfiguration(JWTConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

}
