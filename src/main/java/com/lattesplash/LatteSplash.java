package com.lattesplash;

import com.lattesplash.api.*;
import com.lattesplash.auth.AuthProvider;
import com.lattesplash.http.HttpClient;
import com.lattesplash.http.OkHttpClientAdapter;
import com.lattesplash.model.LatteSplashConfig;
import com.lattesplash.model.QueryParams;
import com.lattesplash.util.UrlConfig;
import com.lattesplash.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Main entry point for the LatteSplash Unsplash API wrapper.
 *
 * <p>Provides access to all Unsplash API endpoints through typed sub-API classes.</p>
 *
 * <pre>{@code
 * LatteSplashConfig config = new LatteSplashConfig.Builder()
 *     .accessKey("your-access-key")
 *     .secretKey("your-secret-key")
 *     .redirectUri("https://example.com/callback")
 *     .code("authorization-code")
 *     .build();
 *
 * try (LatteSplash splash = new LatteSplash(config)) {
 *     LatteSplashResponse profile = splash.users().getPublicProfile("sandeepv");
 * }
 * }</pre>
 */
public class LatteSplash implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(LatteSplash.class);

    private final LatteSplashConfig config;
    private final AuthProvider authProvider;
    private final OkHttpClientAdapter httpClient;

    private final UsersApi usersApi;
    private final PhotosApi photosApi;
    private final SearchApi searchApi;
    private final CollectionsApi collectionsApi;
    private final CurrentUserApi currentUserApi;
    private final StatsApi statsApi;

    public LatteSplash(LatteSplashConfig config) throws LatteSplashError {
        Objects.requireNonNull(config, "Config must not be null");
        this.config = config;
        this.authProvider = new AuthProvider(config);
        this.httpClient = new OkHttpClientAdapter(config, authProvider);

        this.usersApi = new UsersApi(httpClient);
        this.photosApi = new PhotosApi(httpClient, httpClient);
        this.searchApi = new SearchApi(httpClient, httpClient);
        this.collectionsApi = new CollectionsApi(httpClient, httpClient);
        this.currentUserApi = new CurrentUserApi(httpClient, httpClient);
        this.statsApi = new StatsApi(httpClient, httpClient);
    }

    public UsersApi users() { return usersApi; }
    public PhotosApi photos() { return photosApi; }
    public SearchApi search() { return searchApi; }
    public CollectionsApi collections() { return collectionsApi; }
    public CurrentUserApi currentUser() { return currentUserApi; }
    public StatsApi stats() { return statsApi; }

    /**
     * Exchange the authorization code for a bearer token.
     *
     * @return the token response
     * @throws LatteSplashError if required credentials are missing or the request fails
     */
    public LatteSplashResponse generateBearerToken() throws LatteSplashError {
        ValidationUtil.requireNonEmpty(authProvider.getAccessKey(), "access_key");
        ValidationUtil.requireNonEmpty(authProvider.getSecretKey(), "secret_key");
        ValidationUtil.requireNonEmpty(authProvider.getRedirectUri(), "redirect_uri");
        ValidationUtil.requireNonEmpty(authProvider.getCode(), "code");

        Map<String, String> params = new QueryParams()
                .put("client_id", authProvider.getAccessKey())
                .put("client_secret", authProvider.getSecretKey())
                .put("redirect_uri", authProvider.getRedirectUri())
                .put("code", authProvider.getCode())
                .put("grant_type", "authorization_code")
                .build();
        return httpClient.execute(UrlConfig.getInstance().getBearerTokenUrl(),
                HttpClient.HttpMethod.POST, params, null);
    }

    /**
     * Exchange the authorization code for a bearer token asynchronously.
     *
     * @return a CompletableFuture containing the token response
     */
    public CompletableFuture<LatteSplashResponse> generateBearerTokenAsync() {
        try {
            return CompletableFuture.completedFuture(generateBearerToken());
        } catch (LatteSplashError e) {
            log.debug("generateBearerTokenAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public void close() {
        httpClient.close();
    }
}
