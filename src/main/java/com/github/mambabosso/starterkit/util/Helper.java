package com.github.mambabosso.starterkit.util;

import org.joda.time.Duration;

import java.util.Arrays;

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

    public static Class<?>[] classArray(final Class<?> ...classes) {
        return Arrays.copyOf(classes, classes.length);
    }

    public static Object[] objectArray(final Object ...objects) {
        return Arrays.copyOf(objects, objects.length);
    }

    public static Duration toJodaTimeDuration(final java.time.Duration duration) {
        try {
            return Duration.millis(duration.toMillis());
        } catch (Exception ex) {
            return Duration.ZERO;
        }
    }

    public static Duration toJodaTimeDuration(final io.dropwizard.util.Duration duration) {
        try {
            return Duration.millis(duration.toMilliseconds());
        } catch (Exception ex) {
            return Duration.ZERO;
        }
    }

}
