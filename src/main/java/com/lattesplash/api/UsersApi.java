package com.lattesplash.api;

import com.lattesplash.LatteSplashError;
import com.lattesplash.LatteSplashResponse;
import com.lattesplash.http.HttpClient;
import com.lattesplash.model.QueryParams;
import com.lattesplash.util.UrlConfig;
import com.lattesplash.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Unsplash Users API endpoints.
 */
public class UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApi.class);
    private static final List<String> AVAILABLE_ORDERS = Arrays.asList("latest", "oldest", "popular");

    private final HttpClient httpClient;

    public UsersApi(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public LatteSplashResponse getPublicProfile(String username, Integer width, Integer height)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(username, "username");
        Map<String, String> params = new QueryParams()
                .put("w", width)
                .put("h", height)
                .build();
        return httpClient.execute(
                UrlConfig.getInstance().resolveUrl("USERS_PUBLIC_PROFILE", Map.of("username", username)),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> getPublicProfileAsync(String username, Integer width, Integer height) {
        try {
            return CompletableFuture.completedFuture(getPublicProfile(username, width, height));
        } catch (LatteSplashError e) {
            log.debug("getPublicProfileAsync failed for username={}: {}", username, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getUserPortfolio(String username) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(username, "username");
        return httpClient.execute(
                UrlConfig.getInstance().resolveUrl("USERS_PORTFOLIO", Map.of("username", username)),
                HttpClient.HttpMethod.GET, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> getUserPortfolioAsync(String username) {
        try {
            return CompletableFuture.completedFuture(getUserPortfolio(username));
        } catch (LatteSplashError e) {
            log.debug("getUserPortfolioAsync failed for username={}: {}", username, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getUserPhotos(String username, Integer page, Integer perPage,
                                              Boolean stats, String resolution, Integer quantity,
                                              String orderBy) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(username, "username");
        ValidationUtil.requireSupportedValue(orderBy, AVAILABLE_ORDERS, "order_by");

        Map<String, String> params = new QueryParams()
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .put("order_by", orderBy != null ? orderBy : "latest")
                .put("stats", stats != null ? stats : false)
                .put("resolution", resolution != null ? resolution : "days")
                .put("quantity", quantity != null ? quantity : 30)
                .build();
        return httpClient.execute(
                UrlConfig.getInstance().resolveUrl("USERS_PHOTOS", Map.of("username", username)),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> getUserPhotosAsync(String username, Integer page, Integer perPage,
                                                                      Boolean stats, String resolution, Integer quantity,
                                                                      String orderBy) {
        try {
            return CompletableFuture.completedFuture(getUserPhotos(username, page, perPage, stats, resolution, quantity, orderBy));
        } catch (LatteSplashError e) {
            log.debug("getUserPhotosAsync failed for username={}: {}", username, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getUserLikedPhotos(String username, Integer page, Integer perPage,
                                                    String orderBy) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(username, "username");
        ValidationUtil.requireSupportedValue(orderBy, AVAILABLE_ORDERS, "order_by");

        Map<String, String> params = new QueryParams()
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .put("order_by", orderBy != null ? orderBy : "latest")
                .build();
        return httpClient.execute(
                UrlConfig.getInstance().resolveUrl("USERS_LIKED_PHOTOS", Map.of("username", username)),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> getUserLikedPhotosAsync(String username, Integer page, Integer perPage,
                                                                           String orderBy) {
        try {
            return CompletableFuture.completedFuture(getUserLikedPhotos(username, page, perPage, orderBy));
        } catch (LatteSplashError e) {
            log.debug("getUserLikedPhotosAsync failed for username={}: {}", username, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getUserCollections(String username, Integer page, Integer perPage)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(username, "username");

        Map<String, String> params = new QueryParams()
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .build();
        return httpClient.execute(
                UrlConfig.getInstance().resolveUrl("USERS_COLLECTIONS", Map.of("username", username)),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> getUserCollectionsAsync(String username, Integer page, Integer perPage) {
        try {
            return CompletableFuture.completedFuture(getUserCollections(username, page, perPage));
        } catch (LatteSplashError e) {
            log.debug("getUserCollectionsAsync failed for username={}: {}", username, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getUserStatistics(String username, String resolution, Integer quantity)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(username, "username");

        Map<String, String> params = new QueryParams()
                .put("resolution", resolution != null ? resolution : "days")
                .put("quantity", quantity != null ? quantity : 30)
                .build();
        return httpClient.execute(
                UrlConfig.getInstance().resolveUrl("USERS_STATISTICS", Map.of("username", username)),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> getUserStatisticsAsync(String username, String resolution, Integer quantity) {
        try {
            return CompletableFuture.completedFuture(getUserStatistics(username, resolution, quantity));
        } catch (LatteSplashError e) {
            log.debug("getUserStatisticsAsync failed for username={}: {}", username, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}
