package com.lattesplash.auth;

import com.lattesplash.LatteSplashError;
import com.lattesplash.model.LatteSplashConfig;
import com.lattesplash.util.HashUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides authentication headers for Unsplash API requests.
 *
 * <p>Supports both Bearer token and Client-ID authentication modes.</p>
 */
public class AuthProvider {

    private final LatteSplashConfig config;
    private final Map<String, String> headers;

    public AuthProvider(LatteSplashConfig config) throws LatteSplashError {
        this.config = config;
        this.headers = new LinkedHashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.headers.put("X-Requested-With", "LatteSplash");

        if (config.hasBearerToken()) {
            this.headers.put("Authorization", "Bearer " + config.getBearerToken());
            this.headers.put("X-LatteSplash-Header", HashUtil.sha256(config.getBearerToken()));
        } else {
            validateOAuthCredentials();
            this.headers.put("Authorization", "Client-ID " + config.getAccessKey());
            this.headers.put("X-LatteSplash-Header", HashUtil.sha256(config.getAccessKey()));
        }
    }

    public Map<String, String> getHeaders() {
        return new LinkedHashMap<>(headers);
    }

    public String getAccessKey() {
        return config.getAccessKey();
    }

    public String getSecretKey() {
        return config.getSecretKey();
    }

    public String getRedirectUri() {
        return config.getRedirectUri();
    }

    public String getCode() {
        return config.getCode();
    }

    private void validateOAuthCredentials() throws LatteSplashError {
        if (config.getAccessKey() == null || config.getAccessKey().isEmpty()) {
            throw new LatteSplashError("Access Key missing!");
        }
        if (config.getSecretKey() == null || config.getSecretKey().isEmpty()) {
            throw new LatteSplashError("Secret Key missing!");
        }
        if (config.getRedirectUri() == null || config.getRedirectUri().isEmpty()) {
            throw new LatteSplashError("Redirect URI missing!");
        }
        if (config.getCode() == null || config.getCode().isEmpty()) {
            throw new LatteSplashError("Authorization Code missing!");
        }
    }
}
