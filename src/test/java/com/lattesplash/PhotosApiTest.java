package com.lattesplash;

import com.lattesplash.api.PhotosApi;
import com.lattesplash.http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for PhotosApi methods, mirroring the JS test suite.
 */
class PhotosApiTest {

    private MockHttpClient mockClient;
    private PhotosApi photosApi;

    @BeforeEach
    void setUp() {
        mockClient = new MockHttpClient();
        photosApi = new PhotosApi(mockClient, mockClient);
    }

    @Test
    void getPhotoBuildsWidthHeightAndRect() throws LatteSplashError {
        photosApi.getPhoto("g3PyXO4A0yc", 100, 200, "0,0,100,200");
        assertEquals("https://api.unsplash.com/photos/g3PyXO4A0yc", mockClient.getLastUrl());
        assertEquals("100", mockClient.getLastParams().get("w"));
        assertEquals("200", mockClient.getLastParams().get("h"));
        assertEquals("0,0,100,200", mockClient.getLastParams().get("rect"));
    }

    @Test
    void getPhotoThrowsForMissingId() {
        assertThrows(LatteSplashError.class, () -> photosApi.getPhoto("", null, null, null));
    }

    @Test
    void getRandomPhotoIncludesAllParams() throws LatteSplashError {
        photosApi.getRandomPhoto("123", true, "sandeepv", "nature", 400, 300, "portrait", 2);
        assertEquals("https://api.unsplash.com/photos/random", mockClient.getLastUrl());
        assertEquals("123", mockClient.getLastParams().get("collections"));
        assertEquals("true", mockClient.getLastParams().get("featured"));
        assertEquals("sandeepv", mockClient.getLastParams().get("username"));
        assertEquals("nature", mockClient.getLastParams().get("query"));
        assertEquals("400", mockClient.getLastParams().get("width"));
        assertEquals("300", mockClient.getLastParams().get("height"));
        assertEquals("portrait", mockClient.getLastParams().get("orientation"));
        assertEquals("2", mockClient.getLastParams().get("count"));
    }

    @Test
    void getRandomPhotoThrowsForInvalidOrientation() {
        assertThrows(LatteSplashError.class,
                () -> photosApi.getRandomPhoto(null, null, null, null, null, null, "invalid", null));
    }

    @Test
    void getPhotoStatisticsSendsCorrectParams() throws LatteSplashError {
        photosApi.getPhotoStatistics("g3PyXO4A0yc", "weeks", 10);
        assertEquals("https://api.unsplash.com/photos/g3PyXO4A0yc/statistics", mockClient.getLastUrl());
        assertEquals("weeks", mockClient.getLastParams().get("resolution"));
        assertEquals("10", mockClient.getLastParams().get("quantity"));
    }

    @Test
    void getPhotoLinkRequestsDownloadEndpoint() throws LatteSplashError {
        photosApi.getPhotoLink("g3PyXO4A0yc");
        assertEquals("https://api.unsplash.com/photos/g3PyXO4A0yc/download", mockClient.getLastUrl());
    }

    @Test
    void likePhotoPostsToLikeEndpoint() throws LatteSplashError {
        photosApi.likePhoto("g3PyXO4A0yc");
        assertEquals("https://api.unsplash.com/photos/g3PyXO4A0yc/like", mockClient.getLastUrl());
        assertEquals("POST", mockClient.getLastMethod().name());
    }

    @Test
    void unlikePhotoDeletesLikeEndpoint() throws LatteSplashError {
        photosApi.unlikePhoto("g3PyXO4A0yc");
        assertEquals("https://api.unsplash.com/photos/g3PyXO4A0yc/like", mockClient.getLastUrl());
        assertEquals("DELETE", mockClient.getLastMethod().name());
    }

    @Test
    void listPhotosUsesDefaults() throws LatteSplashError {
        photosApi.listPhotos(null, null, null);
        assertEquals("https://api.unsplash.com/photos", mockClient.getLastUrl());
        assertEquals("1", mockClient.getLastParams().get("page"));
        assertEquals("10", mockClient.getLastParams().get("per_page"));
        assertEquals("latest", mockClient.getLastParams().get("order_by"));
    }

    @Test
    void updatePhotoSendsLocationAndExif() throws LatteSplashError {
        Map<String, Object> location = new HashMap<>();
        location.put("latitude", 10.1);
        location.put("longitude", 20.2);
        location.put("name", "Test");

        Map<String, Object> exif = new HashMap<>();
        exif.put("make", "Canon");
        exif.put("model", "EOS");
        exif.put("iso_speed_ratings", 100);

        photosApi.updatePhoto("g3PyXO4A0yc", location, exif);
        assertEquals("https://api.unsplash.com/photos/g3PyXO4A0yc", mockClient.getLastUrl());
        assertEquals("PUT", mockClient.getLastMethod().name());
        assertEquals("10.1", mockClient.getLastParams().get("location[latitude]"));
        assertEquals("20.2", mockClient.getLastParams().get("location[longitude]"));
        assertEquals("Test", mockClient.getLastParams().get("location[name]"));
        assertEquals("Canon", mockClient.getLastParams().get("exif[make]"));
        assertEquals("EOS", mockClient.getLastParams().get("exif[model]"));
        assertEquals("100", mockClient.getLastParams().get("exif[iso_speed_ratings]"));
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
