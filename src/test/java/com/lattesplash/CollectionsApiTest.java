package com.lattesplash;

import com.lattesplash.api.CollectionsApi;
import com.lattesplash.http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CollectionsApi methods, mirroring the JS test suite.
 */
class CollectionsApiTest {

    private MockHttpClient mockClient;
    private CollectionsApi collectionsApi;

    @BeforeEach
    void setUp() {
        mockClient = new MockHttpClient();
        collectionsApi = new CollectionsApi(mockClient, mockClient);
    }

    @Test
    void listCollectionsUsesDefaults() throws LatteSplashError {
        collectionsApi.listCollections(null, null);
        assertEquals("https://api.unsplash.com/collections", mockClient.getLastUrl());
        assertEquals("1", mockClient.getLastParams().get("page"));
        assertEquals("10", mockClient.getLastParams().get("per_page"));
    }

    @Test
    void listFeaturedCollections() throws LatteSplashError {
        collectionsApi.listFeaturedCollections(2, 8);
        assertEquals("https://api.unsplash.com/collections/featured", mockClient.getLastUrl());
        assertEquals("2", mockClient.getLastParams().get("page"));
        assertEquals("8", mockClient.getLastParams().get("per_page"));
    }

    @Test
    void listCuratedCollections() throws LatteSplashError {
        collectionsApi.listCuratedCollections(3, 9);
        assertEquals("https://api.unsplash.com/collections/curated", mockClient.getLastUrl());
        assertEquals("3", mockClient.getLastParams().get("page"));
        assertEquals("9", mockClient.getLastParams().get("per_page"));
    }

    @Test
    void getCollectionUsesId() throws LatteSplashError {
        collectionsApi.getCollection("collection-id");
        assertEquals("https://api.unsplash.com/collections/collection-id", mockClient.getLastUrl());
    }

    @Test
    void getCuratedCollectionUsesId() throws LatteSplashError {
        collectionsApi.getCuratedCollection("curated-id");
        assertEquals("https://api.unsplash.com/collections/curated/curated-id", mockClient.getLastUrl());
    }

    @Test
    void getCollectionPhotosUsesIdAndPagination() throws LatteSplashError {
        collectionsApi.getCollectionPhotos("collection-id", 4, 12);
        assertEquals("https://api.unsplash.com/collections/collection-id/photos", mockClient.getLastUrl());
        assertEquals("4", mockClient.getLastParams().get("page"));
        assertEquals("12", mockClient.getLastParams().get("per_page"));
    }

    @Test
    void getCuratedCollectionPhotosUsesIdAndPagination() throws LatteSplashError {
        collectionsApi.getCuratedCollectionPhotos("curated-id", 5, 13);
        assertEquals("https://api.unsplash.com/collections/curated/curated-id/photos", mockClient.getLastUrl());
        assertEquals("5", mockClient.getLastParams().get("page"));
        assertEquals("13", mockClient.getLastParams().get("per_page"));
    }

    @Test
    void listRelatedCollectionsUsesId() throws LatteSplashError {
        collectionsApi.listRelatedCollections("collection-id");
        assertEquals("https://api.unsplash.com/collections/collection-id/related", mockClient.getLastUrl());
    }

    @Test
    void createCollectionSendsCorrectPayload() throws LatteSplashError {
        collectionsApi.createCollection("My Collection", "desc", true);
        assertEquals("https://api.unsplash.com/collections", mockClient.getLastUrl());
        assertEquals("POST", mockClient.getLastMethod().name());
        assertEquals("My Collection", mockClient.getLastParams().get("title"));
        assertEquals("desc", mockClient.getLastParams().get("description"));
        assertEquals("true", mockClient.getLastParams().get("private"));
    }

    @Test
    void createCollectionThrowsForMissingTitle() {
        assertThrows(LatteSplashError.class,
                () -> collectionsApi.createCollection(null, null, null));
    }

    @Test
    void updateCollectionSendsCorrectPayload() throws LatteSplashError {
        collectionsApi.updateCollection("cid", "Title", "desc2", false);
        assertEquals("https://api.unsplash.com/collections/cid", mockClient.getLastUrl());
        assertEquals("PUT", mockClient.getLastMethod().name());
        assertEquals("Title", mockClient.getLastParams().get("title"));
        assertEquals("desc2", mockClient.getLastParams().get("description"));
        assertEquals("false", mockClient.getLastParams().get("private"));
    }

    @Test
    void deleteCollectionSendsDelete() throws LatteSplashError {
        collectionsApi.deleteCollection("cid");
        assertEquals("https://api.unsplash.com/collections/cid", mockClient.getLastUrl());
        assertEquals("DELETE", mockClient.getLastMethod().name());
    }

    @Test
    void addPhotoToCollectionSendsPost() throws LatteSplashError {
        collectionsApi.addPhotoToCollection("cid", "pid");
        assertEquals("https://api.unsplash.com/collections/cid/add", mockClient.getLastUrl());
        assertEquals("POST", mockClient.getLastMethod().name());
        assertEquals("pid", mockClient.getLastParams().get("photo_id"));
    }

    @Test
    void removePhotoFromCollectionSendsDelete() throws LatteSplashError {
        collectionsApi.removePhotoFromCollection("cid", "pid");
        assertEquals("https://api.unsplash.com/collections/cid/remove", mockClient.getLastUrl());
        assertEquals("DELETE", mockClient.getLastMethod().name());
        assertEquals("pid", mockClient.getLastParams().get("photo_id"));
    }

    static class MockHttpClient implements HttpClient {
        private String lastUrl;
        private HttpMethod lastMethod;
        private Map<String, String> lastParams;

        @Override
        public LatteSplashResponse execute(String url, HttpMethod method,
                                           Map<String, String> queryParams, Object body) {
            this.lastUrl = url;
            this.lastMethod = method;
            this.lastParams = queryParams;
            return new LatteSplashResponse(Map.of());
        }

        @Override
        public void close() {}

        public String getLastUrl() { return lastUrl; }
        public HttpMethod getLastMethod() { return lastMethod; }
        public Map<String, String> getLastParams() { return lastParams; }
    }
}
