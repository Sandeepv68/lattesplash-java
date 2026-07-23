package com.lattesplash;

import com.lattesplash.api.PhotosApi;
import com.lattesplash.http.HttpClient;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for async API methods.
 */
class AsyncApiTest {

    @Test
    void asyncMethodReturnsCompletableFuture() throws Exception {
        MockHttpClient mockClient = new MockHttpClient();
        PhotosApi photosApi = new PhotosApi(mockClient, mockClient);

        CompletableFuture<LatteSplashResponse> future = photosApi.listPhotosAsync(1, 10, null);
        assertNotNull(future);
        assertTrue(future.isDone());

        LatteSplashResponse response = future.get();
        assertNotNull(response);
    }

    @Test
    void asyncMethodHandlesValidationError() {
        MockHttpClient mockClient = new MockHttpClient();
        PhotosApi photosApi = new PhotosApi(mockClient, mockClient);

        CompletableFuture<LatteSplashResponse> future = photosApi.getPhotoAsync("", null, null, null);
        assertNotNull(future);
        assertTrue(future.isCompletedExceptionally());

        ExecutionException ex = assertThrows(ExecutionException.class, future::get);
        assertTrue(ex.getCause() instanceof LatteSplashError);
    }

    @Test
    void asyncGetRandomPhotoHandlesInvalidOrientation() {
        MockHttpClient mockClient = new MockHttpClient();
        PhotosApi photosApi = new PhotosApi(mockClient, mockClient);

        CompletableFuture<LatteSplashResponse> future = photosApi.getRandomPhotoAsync(
                null, null, null, null, null, null, "invalid", null);
        assertTrue(future.isCompletedExceptionally());
    }

    @Test
    void asyncSearchHandlesMissingQuery() {
        MockHttpClient mockClient = new MockHttpClient();
        com.lattesplash.api.SearchApi searchApi = new com.lattesplash.api.SearchApi(mockClient, mockClient);

        CompletableFuture<LatteSplashResponse> future = searchApi.searchPhotosAsync(null, null, null, null, null);
        assertTrue(future.isCompletedExceptionally());
    }

    static class MockHttpClient implements HttpClient {
        @Override
        public LatteSplashResponse execute(String url, HttpMethod method,
                                           java.util.Map<String, String> queryParams, Object body) {
            return new LatteSplashResponse(java.util.Map.of("url", url));
        }

        @Override
        public void close() {}
    }
}
