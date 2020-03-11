package com.github.mambabosso.starterkit.auth;

import com.github.mambabosso.starterkit.user.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

public final class UserAuthenticator implements Authenticator<BasicCredentials, User> {

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        return AuthHelper.authenticate(basicCredentials.getUsername(), basicCredentials.getPassword());
    }

}
