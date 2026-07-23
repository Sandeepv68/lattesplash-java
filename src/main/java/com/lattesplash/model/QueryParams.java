package com.lattesplash.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Fluent builder for query parameters.
 * Strips null, empty, and undefined values automatically.
 */
public final class QueryParams {

    private final Map<String, String> params = new LinkedHashMap<>();

    public QueryParams put(String key, Object value) {
        if (key == null) {
            return this;
        }
        if (value == null) {
            return this;
        }
        String stringValue = value.toString();
        if (stringValue.isEmpty()) {
            return this;
        }
        params.put(key, stringValue);
        return this;
    }

    public QueryParams put(String key, boolean value) {
        if (key == null) {
            return this;
        }
        params.put(key, String.valueOf(value));
        return this;
    }

    public QueryParams put(String key, int value) {
        if (key == null) {
            return this;
        }
        params.put(key, String.valueOf(value));
        return this;
    }

    public Map<String, String> build() {
        return new LinkedHashMap<>(params);
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }
}
