package com.github.mambabosso.starterkit.util;

public final class Helper {

    public static boolean containsWhitespace(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }

}
