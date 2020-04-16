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

    public static Class<?>[] classArray(int length, Class<?> ...classes) {
        if (length != classes.length) {
            return null;
        }
        Class<?>[] result = new Class<?>[length];
        System.arraycopy(classes, 0, result, 0, length);
        return result;
    }

    public static Object[] objectArray(int length, Object ...objects) {
        if (length != objects.length) {
            return null;
        }
        Object[] result = new Object[length];
        System.arraycopy(objects, 0, result, 0, length);
        return result;
    }

}
