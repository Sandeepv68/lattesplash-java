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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Unsplash Photos API endpoints.
 */
public class PhotosApi {

    private static final Logger log = LoggerFactory.getLogger(PhotosApi.class);
    private static final List<String> AVAILABLE_ORDERS = Arrays.asList("latest", "oldest", "popular");
    private static final List<String> AVAILABLE_ORIENTATIONS = Arrays.asList("landscape", "portrait", "squarish");

    private final HttpClient httpClient;
    private final HttpClient asyncClient;

    public PhotosApi(HttpClient httpClient, HttpClient asyncClient) {
        this.httpClient = httpClient;
        this.asyncClient = asyncClient;
    }

    public LatteSplashResponse listPhotos(Integer page, Integer perPage, String orderBy)
            throws LatteSplashError {
        ValidationUtil.requireSupportedValue(orderBy, AVAILABLE_ORDERS, "order_by");

        Map<String, String> params = new QueryParams()
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .put("order_by", orderBy != null ? orderBy : "latest")
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("LIST_PHOTOS", null),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> listPhotosAsync(Integer page, Integer perPage, String orderBy) {
        try {
            return CompletableFuture.completedFuture(listPhotos(page, perPage, orderBy));
        } catch (LatteSplashError e) {
            log.debug("listPhotosAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse listCuratedPhotos(Integer page, Integer perPage, String orderBy)
            throws LatteSplashError {
        ValidationUtil.requireSupportedValue(orderBy, AVAILABLE_ORDERS, "order_by");

        Map<String, String> params = new QueryParams()
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .put("order_by", orderBy != null ? orderBy : "latest")
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("LIST_CURATED_PHOTOS", null),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> listCuratedPhotosAsync(Integer page, Integer perPage, String orderBy) {
        try {
            return CompletableFuture.completedFuture(listCuratedPhotos(page, perPage, orderBy));
        } catch (LatteSplashError e) {
            log.debug("listCuratedPhotosAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getPhoto(String id, Integer width, Integer height, String rect)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");

        Map<String, String> params = new QueryParams()
                .put("w", width)
                .put("h", height)
                .put("rect", rect)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("GET_A_PHOTO", Map.of("id", id)),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> getPhotoAsync(String id, Integer width, Integer height, String rect) {
        try {
            return CompletableFuture.completedFuture(getPhoto(id, width, height, rect));
        } catch (LatteSplashError e) {
            log.debug("getPhotoAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getRandomPhoto(String collections, Boolean featured, String username,
                                               String query, Integer width, Integer height,
                                               String orientation, Integer count) throws LatteSplashError {
        ValidationUtil.requireSupportedValue(orientation, AVAILABLE_ORIENTATIONS, "orientation");

        Map<String, String> params = new QueryParams()
                .put("collections", collections)
                .put("featured", featured != null ? featured : false)
                .put("username", username)
                .put("query", query)
                .put("width", width)
                .put("height", height)
                .put("orientation", orientation != null ? orientation : "landscape")
                .put("count", count != null ? count : 1)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("GET_A_RANDOM_PHOTO", null),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> getRandomPhotoAsync(String collections, Boolean featured,
                                                                       String username, String query,
                                                                       Integer width, Integer height,
                                                                       String orientation, Integer count) {
        try {
            return CompletableFuture.completedFuture(getRandomPhoto(collections, featured, username, query, width, height, orientation, count));
        } catch (LatteSplashError e) {
            log.debug("getRandomPhotoAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getPhotoStatistics(String id, String resolution, Integer quantity)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");

        Map<String, String> params = new QueryParams()
                .put("resolution", resolution != null ? resolution : "days")
                .put("quantity", quantity != null ? quantity : 30)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("GET_A_PHOTO_STATISTICS", Map.of("id", id)),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> getPhotoStatisticsAsync(String id, String resolution, Integer quantity) {
        try {
            return CompletableFuture.completedFuture(getPhotoStatistics(id, resolution, quantity));
        } catch (LatteSplashError e) {
            log.debug("getPhotoStatisticsAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getPhotoLink(String id) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("GET_A_PHOTO_DOWNLOAD_LINK", Map.of("id", id)),
                HttpClient.HttpMethod.GET, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> getPhotoLinkAsync(String id) {
        try {
            return CompletableFuture.completedFuture(getPhotoLink(id));
        } catch (LatteSplashError e) {
            log.debug("getPhotoLinkAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse updatePhoto(String id, Map<String, Object> location,
                                            Map<String, Object> exif) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");

        Map<String, String> params = new HashMap<>();
        if (location != null) {
            addIfPresent(params, "location[latitude]", location.get("latitude"));
            addIfPresent(params, "location[longitude]", location.get("longitude"));
            addIfPresent(params, "location[name]", location.get("name"));
            addIfPresent(params, "location[city]", location.get("city"));
            addIfPresent(params, "location[country]", location.get("country"));
            addIfPresent(params, "location[confidential]", location.get("confidential"));
        }
        if (exif != null) {
            addIfPresent(params, "exif[make]", exif.get("make"));
            addIfPresent(params, "exif[model]", exif.get("model"));
            addIfPresent(params, "exif[exposure_time]", exif.get("exposure_time"));
            addIfPresent(params, "exif[aperture_value]", exif.get("aperture_value"));
            addIfPresent(params, "exif[focal_length]", exif.get("focal_length"));
            addIfPresent(params, "exif[iso_speed_ratings]", exif.get("iso_speed_ratings"));
        }

        return httpClient.execute(UrlConfig.getInstance().resolveUrl("UPDATE_A_PHOTO", Map.of("id", id)),
                HttpClient.HttpMethod.PUT, params, null);
    }

    public CompletableFuture<LatteSplashResponse> updatePhotoAsync(String id, Map<String, Object> location,
                                                                    Map<String, Object> exif) {
        try {
            return CompletableFuture.completedFuture(updatePhoto(id, location, exif));
        } catch (LatteSplashError e) {
            log.debug("updatePhotoAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse likePhoto(String id) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("LIKE_A_PHOTO", Map.of("id", id)),
                HttpClient.HttpMethod.POST, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> likePhotoAsync(String id) {
        try {
            return CompletableFuture.completedFuture(likePhoto(id));
        } catch (LatteSplashError e) {
            log.debug("likePhotoAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse unlikePhoto(String id) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("UNLIKE_A_PHOTO", Map.of("id", id)),
                HttpClient.HttpMethod.DELETE, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> unlikePhotoAsync(String id) {
        try {
            return CompletableFuture.completedFuture(unlikePhoto(id));
        } catch (LatteSplashError e) {
            log.debug("unlikePhotoAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    private void addIfPresent(Map<String, String> params, String key, Object value) {
        if (value != null) {
            params.put(key, value.toString());
        }
    }
}
