package com.github.mambabosso.starterkit.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class DataMap implements Serializable {

    @JsonProperty("data")
    private Map<String, String> dataMap;

    public DataMap() {
    }

    public String tryGet(String key) {
        if (key != null && !key.isEmpty() && dataMap != null && dataMap.containsKey(key)) {
            return dataMap.get(key);
        }
        return null;
    }

    public Map<String, String> getDataMap() {
        return Collections.unmodifiableMap(dataMap);
    }

    public void setDataMap(Map<String, String> dataMap) {
        this.dataMap = Objects.requireNonNull(dataMap);
    }

    public static DataMap create() {
        DataMap result = new DataMap();
        result.setDataMap(new HashMap<String, String>());
        return result;
    }

}
