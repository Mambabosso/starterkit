package com.github.mambabosso.starterkit.util;

import java.util.regex.Pattern;

public final class Validator {

    private static final String NAME_REGEX = "\\b[a-zA-Z][a-zA-Z0-9\\-._]{3,}\\b";
    private static final String MAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        return pattern.matcher(name).matches();
    }

    public static boolean isValidMail(String mail) {
        Pattern pattern = Pattern.compile(MAIL_REGEX);
        return pattern.matcher(mail).matches();
    }

}
