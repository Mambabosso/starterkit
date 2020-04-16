package com.github.mambabosso.starterkit.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.util.Result;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Objects;

public final class Handler {

    public static Result<String> encode(JWTConfiguration jwtConfiguration, Long userId) {
        try {
            Objects.requireNonNull(jwtConfiguration);
            Objects.requireNonNull(userId);

            Algorithm algorithm = Algorithm.HMAC256(jwtConfiguration.getSecretKey());

            JWTCreator.Builder builder = JWT.create();
            builder.withIssuer(jwtConfiguration.getIssuer());

            DateTime now = DateTime.now();
            builder.withIssuedAt(now.toDate());
            builder.withExpiresAt(now.plus(Duration.millis(jwtConfiguration.getLifetime().toMilliseconds())).toDate());

            builder.withClaim("uid", userId);

            return Result.success(builder.sign(algorithm));

        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    public static Result<Long> decode(JWTConfiguration jwtConfiguration, String token) {
        try {
            Objects.requireNonNull(jwtConfiguration);
            Objects.requireNonNull(token);

            Algorithm algorithm = Algorithm.HMAC256(jwtConfiguration.getSecretKey());

            Verification verification = JWT.require(algorithm);
            verification.withIssuer(jwtConfiguration.getIssuer());

            DecodedJWT jwt = verification.build().verify(token);

            return Result.success(jwt.getClaim("uid").asLong());

        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
