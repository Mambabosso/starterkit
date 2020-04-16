package com.github.mambabosso.starterkit.jwt;

import io.dropwizard.util.Duration;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
public class JWTConfiguration {

    @Valid
    @NotEmpty
    private String issuer;

    @Valid
    @NotEmpty
    private String secretKey;

    @Valid
    private Duration lifetime;

}
