package com.lattesplash.model;

import java.util.Objects;

/**
 * Immutable configuration for the LatteSplash client.
 *
 * <p>Use {@link Builder} to construct instances.</p>
 */
public final class LatteSplashConfig {

    private final String accessKey;
    private final String secretKey;
    private final String redirectUri;
    private final String code;
    private final String bearerToken;
    private final long timeout;
    private final int retries;
    private final long retryDelayMs;

    private LatteSplashConfig(Builder builder) {
        this.accessKey = builder.accessKey;
        this.secretKey = builder.secretKey;
        this.redirectUri = builder.redirectUri;
        this.code = builder.code;
        this.bearerToken = builder.bearerToken;
        this.timeout = builder.timeout;
        this.retries = builder.retries;
        this.retryDelayMs = builder.retryDelayMs;
    }

    public String getAccessKey() { return accessKey; }
    public String getSecretKey() { return secretKey; }
    public String getRedirectUri() { return redirectUri; }
    public String getCode() { return code; }
    public String getBearerToken() { return bearerToken; }
    public long getTimeout() { return timeout; }
    public int getRetries() { return retries; }
    public long getRetryDelayMs() { return retryDelayMs; }

    public boolean hasBearerToken() {
        return bearerToken != null && !bearerToken.isEmpty();
    }

    public static final class Builder {
        private String accessKey;
        private String secretKey;
        private String redirectUri;
        private String code;
        private String bearerToken;
        private long timeout = 10000;
        private int retries = 2;
        private long retryDelayMs = 100;

        public Builder accessKey(String accessKey) {
            this.accessKey = accessKey;
            return this;
        }

        public Builder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public Builder redirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder bearerToken(String bearerToken) {
            this.bearerToken = bearerToken;
            return this;
        }

        public Builder timeout(long timeout) {
            if (timeout <= 0) {
                throw new IllegalArgumentException("Timeout must be positive");
            }
            this.timeout = timeout;
            return this;
        }

        public Builder retries(int retries) {
            if (retries < 0) {
                throw new IllegalArgumentException("Retries must be non-negative");
            }
            this.retries = retries;
            return this;
        }

        public Builder retryDelayMs(long retryDelayMs) {
            if (retryDelayMs < 0) {
                throw new IllegalArgumentException("Retry delay must be non-negative");
            }
            this.retryDelayMs = retryDelayMs;
            return this;
        }

        public LatteSplashConfig build() {
            return new LatteSplashConfig(this);
        }
    }
}
