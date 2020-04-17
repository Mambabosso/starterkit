package com.github.mambabosso.starterkit.jwt;

import com.github.mambabosso.starterkit.util.Helper;
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
    private String secret;

    @Valid
    private Duration lifetime;

    public org.joda.time.Duration lifetime() {
        return Helper.toJodaTimeDuration(lifetime);
    }

}
