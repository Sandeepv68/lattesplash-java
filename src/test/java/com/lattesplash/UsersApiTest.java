package com.lattesplash;

import com.lattesplash.api.*;
import com.lattesplash.http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for UsersApi methods, mirroring the JS test suite.
 */
class UsersApiTest {

    private MockHttpClient mockClient;
    private UsersApi usersApi;

    @BeforeEach
    void setUp() {
        mockClient = new MockHttpClient();
        usersApi = new UsersApi(mockClient);
    }

    @Test
    void getPublicProfileUsesUsernameAndWidthHeight() throws LatteSplashError {
        usersApi.getPublicProfile("sandeepv", 200, 300);
        assertEquals("https://api.unsplash.com/users/sandeepv", mockClient.getLastUrl());
        assertEquals("GET", mockClient.getLastMethod().name());
        assertEquals("200", mockClient.getLastParams().get("w"));
        assertEquals("300", mockClient.getLastParams().get("h"));
    }

    @Test
    void getPublicProfileThrowsForEmptyUsername() {
        assertThrows(LatteSplashError.class, () -> usersApi.getPublicProfile("", null, null));
    }

    @Test
    void getUserPortfolioRequestsCorrectEndpoint() throws LatteSplashError {
        usersApi.getUserPortfolio("sandeepv");
        assertEquals("https://api.unsplash.com/users/sandeepv/portfolio", mockClient.getLastUrl());
    }

    @Test
    void getUserPhotosSendsDefaultPagination() throws LatteSplashError {
        usersApi.getUserPhotos("sandeepv", null, null, null, null, null, null);
        assertEquals("https://api.unsplash.com/users/sandeepv/photos", mockClient.getLastUrl());
        assertEquals("1", mockClient.getLastParams().get("page"));
        assertEquals("10", mockClient.getLastParams().get("per_page"));
        assertEquals("latest", mockClient.getLastParams().get("order_by"));
        assertEquals("false", mockClient.getLastParams().get("stats"));
        assertEquals("days", mockClient.getLastParams().get("resolution"));
        assertEquals("30", mockClient.getLastParams().get("quantity"));
    }

    @Test
    void getUserLikedPhotosSupportsCustomOrderBy() throws LatteSplashError {
        usersApi.getUserLikedPhotos("sandeepv", 2, 5, "popular");
        assertEquals("https://api.unsplash.com/users/sandeepv/likes", mockClient.getLastUrl());
        assertEquals("2", mockClient.getLastParams().get("page"));
        assertEquals("5", mockClient.getLastParams().get("per_page"));
        assertEquals("popular", mockClient.getLastParams().get("order_by"));
    }

    @Test
    void getUserCollectionsUsesDefaults() throws LatteSplashError {
        usersApi.getUserCollections("sandeepv", null, null);
        assertEquals("https://api.unsplash.com/users/sandeepv/collections", mockClient.getLastUrl());
        assertEquals("1", mockClient.getLastParams().get("page"));
        assertEquals("10", mockClient.getLastParams().get("per_page"));
    }

    @Test
    void getUserStatisticsSendsDefaults() throws LatteSplashError {
        usersApi.getUserStatistics("sandeepv", null, null);
        assertEquals("https://api.unsplash.com/users/sandeepv/statistics", mockClient.getLastUrl());
        assertEquals("days", mockClient.getLastParams().get("resolution"));
        assertEquals("30", mockClient.getLastParams().get("quantity"));
    }

    @Test
    void getUserPhotosThrowsForUnsupportedOrderBy() {
        assertThrows(LatteSplashError.class,
                () -> usersApi.getUserPhotos("sandeepv", null, null, null, null, null, "invalid_order"));
    }

    @Test
    void getUserLikedPhotosThrowsForUnsupportedOrderBy() {
        assertThrows(LatteSplashError.class,
                () -> usersApi.getUserLikedPhotos("sandeepv", null, null, "bad_order"));
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
