package com.github.mambabosso.starterkit.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.github.mambabosso.starterkit.error.Errors;
import com.github.mambabosso.starterkit.model.user.User;
import com.github.mambabosso.starterkit.util.Result;
import com.github.mambabosso.starterkit.util.Serializer;
import org.joda.time.DateTime;

import java.util.Objects;

public final class JWTHandler {

    public static Result<String> encode(JWTConfiguration jwtConfiguration, User user) {
        try {
            Objects.requireNonNull(jwtConfiguration);
            Objects.requireNonNull(user);

            Algorithm algorithm = Algorithm.HMAC256(jwtConfiguration.getSecret());

            JWTCreator.Builder builder = JWT.create();
            builder.withIssuer(jwtConfiguration.getIssuer());

            DateTime now = DateTime.now();
            builder.withIssuedAt(now.toDate());
            builder.withExpiresAt(now.plus(jwtConfiguration.lifetime()).toDate());

            builder.withClaim("user", Serializer.toMap(user));

            String token = builder.sign(algorithm);

            return Result.success(token);

        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

    public static Result<User> decode(JWTConfiguration jwtConfiguration, String token) {
        try {
            Objects.requireNonNull(jwtConfiguration);
            Objects.requireNonNull(token);

            Algorithm algorithm = Algorithm.HMAC256(jwtConfiguration.getSecret());

            Verification verification = JWT.require(algorithm);
            verification.withIssuer(jwtConfiguration.getIssuer());

            DecodedJWT jwt = verification.build().verify(token);

            User user = Serializer.fromMap(jwt.getClaim("user").asMap(), User.class);

            return Result.success(user);

        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
