package com.lattesplash.api;

import com.lattesplash.LatteSplashError;
import com.lattesplash.LatteSplashResponse;
import com.lattesplash.http.HttpClient;
import com.lattesplash.model.QueryParams;
import com.lattesplash.util.UrlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Unsplash Current User API endpoints.
 */
public class CurrentUserApi {

    private static final Logger log = LoggerFactory.getLogger(CurrentUserApi.class);

    private final HttpClient httpClient;
    private final HttpClient asyncClient;

    public CurrentUserApi(HttpClient httpClient, HttpClient asyncClient) {
        this.httpClient = httpClient;
        this.asyncClient = asyncClient;
    }

    public LatteSplashResponse getCurrentUserProfile() throws LatteSplashError {
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("CURRENT_USER_PROFILE", null),
                HttpClient.HttpMethod.GET, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> getCurrentUserProfileAsync() {
        try {
            return CompletableFuture.completedFuture(getCurrentUserProfile());
        } catch (LatteSplashError e) {
            log.debug("getCurrentUserProfileAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse updateCurrentUserProfile(String username, String firstName, String lastName,
                                                         String email, String url, String location,
                                                         String bio, String instagramUsername) throws LatteSplashError {
        Map<String, String> params = new QueryParams()
                .put("username", username)
                .put("first_name", firstName)
                .put("last_name", lastName)
                .put("email", email)
                .put("url", url)
                .put("location", location)
                .put("bio", bio)
                .put("instagram_username", instagramUsername)
                .build();
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("UPDATE_CURRENT_USER_PROFILE", null),
                HttpClient.HttpMethod.PUT, params, null);
    }

    public CompletableFuture<LatteSplashResponse> updateCurrentUserProfileAsync(String username, String firstName,
                                                                                 String lastName, String email,
                                                                                 String url, String location,
                                                                                 String bio, String instagramUsername) {
        try {
            return CompletableFuture.completedFuture(updateCurrentUserProfile(username, firstName, lastName,
                    email, url, location, bio, instagramUsername));
        } catch (LatteSplashError e) {
            log.debug("updateCurrentUserProfileAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}
