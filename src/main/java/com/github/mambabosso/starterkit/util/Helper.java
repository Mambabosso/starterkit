package com.github.mambabosso.starterkit.util;

import java.util.regex.Pattern;

public final class Helper {

    public static boolean containsWhitespace(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidName(String name) {
        return !containsWhitespace(name);
    }

    public static boolean isValidMail(String mail) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(mail).matches();
    }

}
