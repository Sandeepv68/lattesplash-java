package com.lattesplash;

import com.lattesplash.api.SearchApi;
import com.lattesplash.http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SearchApi methods, mirroring the JS test suite.
 */
class SearchApiTest {

    private MockHttpClient mockClient;
    private SearchApi searchApi;

    @BeforeEach
    void setUp() {
        mockClient = new MockHttpClient();
        searchApi = new SearchApi(mockClient, mockClient);
    }

    @Test
    void searchPhotosSendsCorrectParams() throws LatteSplashError {
        searchApi.searchPhotos("ocean", 2, 15, "123", "landscape");
        assertEquals("https://api.unsplash.com/search/photos", mockClient.getLastUrl());
        assertEquals("ocean", mockClient.getLastParams().get("query"));
        assertEquals("2", mockClient.getLastParams().get("page"));
        assertEquals("15", mockClient.getLastParams().get("per_page"));
        assertEquals("123", mockClient.getLastParams().get("collections"));
        assertEquals("landscape", mockClient.getLastParams().get("orientation"));
    }

    @Test
    void searchCollectionsSendsCorrectParams() throws LatteSplashError {
        searchApi.searchCollections("travel", 3, 20);
        assertEquals("https://api.unsplash.com/search/collections", mockClient.getLastUrl());
        assertEquals("travel", mockClient.getLastParams().get("query"));
        assertEquals("3", mockClient.getLastParams().get("page"));
        assertEquals("20", mockClient.getLastParams().get("per_page"));
    }

    @Test
    void searchUsersSendsCorrectParams() throws LatteSplashError {
        searchApi.searchUsers("john", 4, 5);
        assertEquals("https://api.unsplash.com/search/users", mockClient.getLastUrl());
        assertEquals("john", mockClient.getLastParams().get("query"));
        assertEquals("4", mockClient.getLastParams().get("page"));
        assertEquals("5", mockClient.getLastParams().get("per_page"));
    }

    @Test
    void searchThrowsForMissingQuery() {
        assertThrows(LatteSplashError.class, () -> searchApi.searchPhotos(null, null, null, null, null));
    }

    @Test
    void searchCollectionsThrowsForMissingQuery() {
        assertThrows(LatteSplashError.class, () -> searchApi.searchCollections(null, null, null));
    }

    @Test
    void searchUsersThrowsForMissingQuery() {
        assertThrows(LatteSplashError.class, () -> searchApi.searchUsers(null, null, null));
    }

    @Test
    void searchThrowsForInvalidOrientation() {
        assertThrows(LatteSplashError.class,
                () -> searchApi.searchPhotos("q", null, null, null, "invalid"));
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
