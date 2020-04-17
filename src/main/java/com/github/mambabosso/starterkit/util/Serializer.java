package com.github.mambabosso.starterkit.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Objects;

public final class Serializer {

    public static <T extends Serializable> String toJSON(final T obj) throws JsonProcessingException {
        Objects.requireNonNull(obj);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    public static <T extends Serializable> T fromJSON(final String json, final Class<T> tClass) throws JsonProcessingException {
        Objects.requireNonNull(json);
        Objects.requireNonNull(tClass);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, tClass);
    }

}
