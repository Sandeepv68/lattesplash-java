package com.lattesplash.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Loads and resolves API endpoint URLs from the classpath url_config.json.
 */
public final class UrlConfig {

    private static final Gson GSON = new Gson();
    private static final Pattern PLACEHOLDER = Pattern.compile(":(\\w+)");

    private static volatile UrlConfig instance;

    private final String apiLocation;
    private final String bearerTokenUrl;
    private final Map<String, String> endpoints;

    private UrlConfig() {
        JsonObject config = loadConfig();
        this.apiLocation = config.get("API_LOCATION").getAsString();
        this.bearerTokenUrl = config.get("BEARER_TOKEN_URL").getAsString();
        this.endpoints = new ConcurrentHashMap<>();

        for (Map.Entry<String, com.google.gson.JsonElement> entry : config.entrySet()) {
            String key = entry.getKey();
            if (!"API_LOCATION".equals(key) && !"BEARER_TOKEN_URL".equals(key)) {
                endpoints.put(key, entry.getValue().getAsString());
            }
        }
    }

    public static UrlConfig getInstance() {
        if (instance == null) {
            synchronized (UrlConfig.class) {
                if (instance == null) {
                    instance = new UrlConfig();
                }
            }
        }
        return instance;
    }

    public String getApiLocation() {
        return apiLocation;
    }

    public String getBearerTokenUrl() {
        return bearerTokenUrl;
    }

    public String getEndpoint(String key) {
        String endpoint = endpoints.get(key);
        if (endpoint == null) {
            throw new IllegalArgumentException("Unknown endpoint: " + key);
        }
        return endpoint;
    }

    public String resolveUrl(String endpointKey, Map<String, String> pathParams) {
        String template = getEndpoint(endpointKey);
        String path = template;
        if (pathParams != null) {
            for (Map.Entry<String, String> entry : pathParams.entrySet()) {
                path = path.replace(":" + entry.getKey(), entry.getValue());
            }
        }
        return apiLocation + path;
    }

    public String resolveAbsoluteUrl(String absoluteUrl) {
        return absoluteUrl;
    }

    private JsonObject loadConfig() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("url_config.json")) {
            if (is == null) {
                throw new IllegalStateException("url_config.json not found on classpath");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return GSON.fromJson(reader, JsonObject.class);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load url_config.json", e);
        }
    }
}
