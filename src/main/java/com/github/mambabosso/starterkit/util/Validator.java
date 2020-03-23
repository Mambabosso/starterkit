package com.github.mambabosso.starterkit.util;

import java.util.regex.Pattern;

public final class Validator {

    private static final String NAME_REGEX = "\\b[a-zA-Z][a-zA-Z0-9\\-._]{3,}\\b";
    private static final String MAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,;])(?=\\S+$).{6,}$";

    public static boolean isValidName(final String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        return pattern.matcher(name).matches();
    }

    public static boolean isValidMail(final String mail) {
        Pattern pattern = Pattern.compile(MAIL_REGEX);
        return pattern.matcher(mail).matches();
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        return pattern.matcher(password).matches();
    }

}
