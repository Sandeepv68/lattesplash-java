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
 * Unsplash Search API endpoints.
 */
public class SearchApi {

    private static final Logger log = LoggerFactory.getLogger(SearchApi.class);
    private static final List<String> AVAILABLE_ORIENTATIONS = Arrays.asList("landscape", "portrait", "squarish");

    private final HttpClient httpClient;
    private final HttpClient asyncClient;

    public SearchApi(HttpClient httpClient, HttpClient asyncClient) {
        this.httpClient = httpClient;
        this.asyncClient = asyncClient;
    }

    public LatteSplashResponse searchPhotos(String query, Integer page, Integer perPage,
                                             String collections, String orientation) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(query, "query");
        ValidationUtil.requireSupportedValue(orientation, AVAILABLE_ORIENTATIONS, "orientation");

        Map<String, String> params = new QueryParams()
                .put("query", query)
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .put("collections", collections)
                .put("orientation", orientation)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("SEARCH_PHOTOS", null),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> searchPhotosAsync(String query, Integer page, Integer perPage,
                                                                      String collections, String orientation) {
        try {
            return CompletableFuture.completedFuture(searchPhotos(query, page, perPage, collections, orientation));
        } catch (LatteSplashError e) {
            log.debug("searchPhotosAsync failed for query={}: {}", query, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse searchCollections(String query, Integer page, Integer perPage)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(query, "query");

        Map<String, String> params = new QueryParams()
                .put("query", query)
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("SEARCH_COLLECTIONS", null),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> searchCollectionsAsync(String query, Integer page, Integer perPage) {
        try {
            return CompletableFuture.completedFuture(searchCollections(query, page, perPage));
        } catch (LatteSplashError e) {
            log.debug("searchCollectionsAsync failed for query={}: {}", query, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse searchUsers(String query, Integer page, Integer perPage)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(query, "query");

        Map<String, String> params = new QueryParams()
                .put("query", query)
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("SEARCH_USERS", null),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> searchUsersAsync(String query, Integer page, Integer perPage) {
        try {
            return CompletableFuture.completedFuture(searchUsers(query, page, perPage));
        } catch (LatteSplashError e) {
            log.debug("searchUsersAsync failed for query={}: {}", query, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}
