package com.github.mambabosso.starterkit.jwt;

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
    @NotEmpty
    private String lifetime;

}
