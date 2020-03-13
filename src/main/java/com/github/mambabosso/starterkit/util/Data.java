package com.github.mambabosso.starterkit.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Data implements Serializable {

    @JsonProperty("data")
    private Map<String, String> dataMap;

    public Data() {
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

    public static Data create() {
        Data result = new Data();
        result.setDataMap(new HashMap<String, String>());
        return result;
    }

}
