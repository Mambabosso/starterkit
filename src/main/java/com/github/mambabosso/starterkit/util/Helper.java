package com.github.mambabosso.starterkit.util;

public final class Helper {

    public static boolean containsWhitespace(final String str) {
        for (char c : str.toCharArray()) {
            if (Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }

    public static int toInteger(final String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            return 0;
        }
    }

}
