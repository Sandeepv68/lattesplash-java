package com.lattesplash;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Typed response wrapper for LatteSplash API calls.
 *
 * <p>Provides convenient accessors over the raw JSON response map.</p>
 */
public final class LatteSplashResponse {

    private static final Gson GSON = new Gson();
    private final Map<String, Object> data;

    public LatteSplashResponse(Map<String, Object> data) {
        this.data = data != null ? Collections.unmodifiableMap(data) : Collections.emptyMap();
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public String getString(String key) {
        Object value = data.get(key);
        return value != null ? value.toString() : null;
    }

    public Integer getInt(String key) {
        Object value = data.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return null;
    }

    public Long getLong(String key) {
        Object value = data.get(key);
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return null;
    }

    public Double getDouble(String key) {
        Object value = data.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return null;
    }

    public Boolean getBoolean(String key) {
        Object value = data.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(String key) {
        Object value = data.get(key);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Object> getList(String key) {
        Object value = data.get(key);
        if (value instanceof List) {
            return (List<Object>) value;
        }
        return null;
    }

    public <T> T get(String key, Class<T> clazz) {
        Object value = data.get(key);
        if (value == null) {
            return null;
        }
        return GSON.fromJson(GSON.toJson(value), clazz);
    }

    public <T> List<T> getList(String key, Class<T> elementClass) {
        Object value = data.get(key);
        if (value == null) {
            return null;
        }
        Type listType = TypeToken.getParameterized(List.class, elementClass).getType();
        return GSON.fromJson(GSON.toJson(value), listType);
    }

    public boolean containsKey(String key) {
        return data.containsKey(key);
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public String toString() {
        return "LatteSplashResponse" + data;
    }
}
