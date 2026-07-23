package com.lattesplash.api;

import com.lattesplash.LatteSplashError;
import com.lattesplash.LatteSplashResponse;
import com.lattesplash.http.HttpClient;
import com.lattesplash.model.QueryParams;
import com.lattesplash.util.UrlConfig;
import com.lattesplash.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Unsplash Collections API endpoints.
 */
public class CollectionsApi {

    private static final Logger log = LoggerFactory.getLogger(CollectionsApi.class);

    private final HttpClient httpClient;
    private final HttpClient asyncClient;

    public CollectionsApi(HttpClient httpClient, HttpClient asyncClient) {
        this.httpClient = httpClient;
        this.asyncClient = asyncClient;
    }

    public LatteSplashResponse listCollections(Integer page, Integer perPage) throws LatteSplashError {
        Map<String, String> params = new QueryParams()
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("LIST_COLLECTIONS", null),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> listCollectionsAsync(Integer page, Integer perPage) {
        try {
            return CompletableFuture.completedFuture(listCollections(page, perPage));
        } catch (LatteSplashError e) {
            log.debug("listCollectionsAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse listFeaturedCollections(Integer page, Integer perPage) throws LatteSplashError {
        Map<String, String> params = new QueryParams()
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("LIST_FEATURED_COLLECTIONS", null),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> listFeaturedCollectionsAsync(Integer page, Integer perPage) {
        try {
            return CompletableFuture.completedFuture(listFeaturedCollections(page, perPage));
        } catch (LatteSplashError e) {
            log.debug("listFeaturedCollectionsAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse listCuratedCollections(Integer page, Integer perPage) throws LatteSplashError {
        Map<String, String> params = new QueryParams()
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("LIST_CURATED_COLLECTIONS", null),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> listCuratedCollectionsAsync(Integer page, Integer perPage) {
        try {
            return CompletableFuture.completedFuture(listCuratedCollections(page, perPage));
        } catch (LatteSplashError e) {
            log.debug("listCuratedCollectionsAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getCollection(String id) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("GET_COLLECTION", Map.of("id", id)),
                HttpClient.HttpMethod.GET, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> getCollectionAsync(String id) {
        try {
            return CompletableFuture.completedFuture(getCollection(id));
        } catch (LatteSplashError e) {
            log.debug("getCollectionAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getCuratedCollection(String id) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("GET_CURATED_COLLECTION", Map.of("id", id)),
                HttpClient.HttpMethod.GET, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> getCuratedCollectionAsync(String id) {
        try {
            return CompletableFuture.completedFuture(getCuratedCollection(id));
        } catch (LatteSplashError e) {
            log.debug("getCuratedCollectionAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getCollectionPhotos(String id, Integer page, Integer perPage)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");
        Map<String, String> params = new QueryParams()
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("GET_COLLECTION_PHOTOS", Map.of("id", id)),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> getCollectionPhotosAsync(String id, Integer page, Integer perPage) {
        try {
            return CompletableFuture.completedFuture(getCollectionPhotos(id, page, perPage));
        } catch (LatteSplashError e) {
            log.debug("getCollectionPhotosAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getCuratedCollectionPhotos(String id, Integer page, Integer perPage)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");
        Map<String, String> params = new QueryParams()
                .put("page", page != null ? page : 1)
                .put("per_page", perPage != null ? perPage : 10)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("GET_CURATED_COLLECTION_PHOTOS", Map.of("id", id)),
                HttpClient.HttpMethod.GET, params, null);
    }

    public CompletableFuture<LatteSplashResponse> getCuratedCollectionPhotosAsync(String id, Integer page, Integer perPage) {
        try {
            return CompletableFuture.completedFuture(getCuratedCollectionPhotos(id, page, perPage));
        } catch (LatteSplashError e) {
            log.debug("getCuratedCollectionPhotosAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse listRelatedCollections(String id) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("LIST_RELATED_COLLECTION", Map.of("id", id)),
                HttpClient.HttpMethod.GET, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> listRelatedCollectionsAsync(String id) {
        try {
            return CompletableFuture.completedFuture(listRelatedCollections(id));
        } catch (LatteSplashError e) {
            log.debug("listRelatedCollectionsAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse createCollection(String title, String description, Boolean isPrivate)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(title, "title");

        Map<String, String> params = new QueryParams()
                .put("title", title)
                .put("description", description)
                .put("private", isPrivate != null ? isPrivate : false)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("CREATE_NEW_COLLECTION", null),
                HttpClient.HttpMethod.POST, params, null);
    }

    public CompletableFuture<LatteSplashResponse> createCollectionAsync(String title, String description, Boolean isPrivate) {
        try {
            return CompletableFuture.completedFuture(createCollection(title, description, isPrivate));
        } catch (LatteSplashError e) {
            log.debug("createCollectionAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse updateCollection(String id, String title, String description, Boolean isPrivate)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");
        ValidationUtil.requireNonEmpty(title, "title");

        Map<String, String> params = new QueryParams()
                .put("title", title)
                .put("description", description)
                .put("private", isPrivate != null ? isPrivate : false)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("UPDATE_EXISTING_COLLECTION", Map.of("id", id)),
                HttpClient.HttpMethod.PUT, params, null);
    }

    public CompletableFuture<LatteSplashResponse> updateCollectionAsync(String id, String title, String description, Boolean isPrivate) {
        try {
            return CompletableFuture.completedFuture(updateCollection(id, title, description, isPrivate));
        } catch (LatteSplashError e) {
            log.debug("updateCollectionAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse deleteCollection(String id) throws LatteSplashError {
        ValidationUtil.requireNonEmpty(id, "id");
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("DELETE_COLLECTION", Map.of("id", id)),
                HttpClient.HttpMethod.DELETE, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> deleteCollectionAsync(String id) {
        try {
            return CompletableFuture.completedFuture(deleteCollection(id));
        } catch (LatteSplashError e) {
            log.debug("deleteCollectionAsync failed for id={}: {}", id, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse addPhotoToCollection(String collectionId, String photoId)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(collectionId, "collection_id");
        ValidationUtil.requireNonEmpty(photoId, "photo_id");

        Map<String, String> params = new QueryParams()
                .put("photo_id", photoId)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("ADD_PHOTO_TO_COLLECTION", Map.of("collection_id", collectionId)),
                HttpClient.HttpMethod.POST, params, null);
    }

    public CompletableFuture<LatteSplashResponse> addPhotoToCollectionAsync(String collectionId, String photoId) {
        try {
            return CompletableFuture.completedFuture(addPhotoToCollection(collectionId, photoId));
        } catch (LatteSplashError e) {
            log.debug("addPhotoToCollectionAsync failed for collectionId={}: {}", collectionId, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse removePhotoFromCollection(String collectionId, String photoId)
            throws LatteSplashError {
        ValidationUtil.requireNonEmpty(collectionId, "collection_id");
        ValidationUtil.requireNonEmpty(photoId, "photo_id");

        Map<String, String> params = new QueryParams()
                .put("photo_id", photoId)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("REMOVE_PHOTO_FROM_COLLECTION", Map.of("collection_id", collectionId)),
                HttpClient.HttpMethod.DELETE, params, null);
    }

    public CompletableFuture<LatteSplashResponse> removePhotoFromCollectionAsync(String collectionId, String photoId) {
        try {
            return CompletableFuture.completedFuture(removePhotoFromCollection(collectionId, photoId));
        } catch (LatteSplashError e) {
            log.debug("removePhotoFromCollectionAsync failed for collectionId={}: {}", collectionId, e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}
