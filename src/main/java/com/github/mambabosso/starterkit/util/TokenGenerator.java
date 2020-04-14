package com.github.mambabosso.starterkit.util;

import java.util.UUID;

public final class TokenGenerator {

    public static String generate() {
        return UUID.randomUUID().toString();
    }

}
