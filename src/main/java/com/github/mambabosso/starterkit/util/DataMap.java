package com.github.mambabosso.starterkit.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class DataMap implements Serializable {

    @JsonProperty("data")
    private Map<String, Serializable> dataMap;

    public DataMap() {
    }

    public <T extends Serializable> T tryGet(final String key, final Class<T> c) {
        if (key != null && !key.isEmpty() && dataMap != null && dataMap.containsKey(key)) {
            return c.cast(dataMap.get(key));
        }
        return null;
    }

    public Map<String, Serializable> getDataMap() {
        return dataMap;
    }

    public void setDataMap(final Map<String, Serializable> dataMap) {
        this.dataMap = Objects.requireNonNull(dataMap);
    }

    public static DataMap create() {
        DataMap result = new DataMap();
        result.setDataMap(new HashMap<String, Serializable>());
        return result;
    }

}
