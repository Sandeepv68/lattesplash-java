package com.lattesplash;

import com.lattesplash.api.CurrentUserApi;
import com.lattesplash.api.StatsApi;
import com.lattesplash.http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CurrentUserApi and StatsApi methods.
 */
class CurrentUserAndStatsApiTest {

    private MockHttpClient mockClient;
    private CurrentUserApi currentUserApi;
    private StatsApi statsApi;

    @BeforeEach
    void setUp() {
        mockClient = new MockHttpClient();
        currentUserApi = new CurrentUserApi(mockClient, mockClient);
        statsApi = new StatsApi(mockClient, mockClient);
    }

    @Test
    void getCurrentUserProfileRequestsMeEndpoint() throws LatteSplashError {
        currentUserApi.getCurrentUserProfile();
        assertEquals("https://api.unsplash.com/me", mockClient.getLastUrl());
        assertEquals("GET", mockClient.getLastMethod().name());
    }

    @Test
    void updateCurrentUserProfileSendsPut() throws LatteSplashError {
        currentUserApi.updateCurrentUserProfile("mock-user", "Mock", "User",
                "mock@example.com", "https://example.com", "Earth", "Testing", "mock_insta");
        assertEquals("https://api.unsplash.com/me", mockClient.getLastUrl());
        assertEquals("PUT", mockClient.getLastMethod().name());
        assertEquals("mock-user", mockClient.getLastParams().get("username"));
        assertEquals("Mock", mockClient.getLastParams().get("first_name"));
        assertEquals("User", mockClient.getLastParams().get("last_name"));
        assertEquals("mock@example.com", mockClient.getLastParams().get("email"));
        assertEquals("https://example.com", mockClient.getLastParams().get("url"));
        assertEquals("Earth", mockClient.getLastParams().get("location"));
        assertEquals("Testing", mockClient.getLastParams().get("bio"));
        assertEquals("mock_insta", mockClient.getLastParams().get("instagram_username"));
    }

    @Test
    void getStatsTotalsRequestsCorrectEndpoint() throws LatteSplashError {
        statsApi.getStatsTotals();
        assertEquals("https://api.unsplash.com/stats/total", mockClient.getLastUrl());
        assertEquals("GET", mockClient.getLastMethod().name());
    }

    @Test
    void getStatsMonthRequestsCorrectEndpoint() throws LatteSplashError {
        statsApi.getStatsMonth();
        assertEquals("https://api.unsplash.com/stats/month", mockClient.getLastUrl());
        assertEquals("GET", mockClient.getLastMethod().name());
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
