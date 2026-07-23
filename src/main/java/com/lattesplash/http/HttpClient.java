package com.lattesplash.http;

import com.lattesplash.LatteSplashError;
import com.lattesplash.LatteSplashResponse;

import java.util.Map;

/**
 * HTTP client abstraction for LatteSplash API calls.
 */
public interface HttpClient {

    enum HttpMethod {
        GET, POST, PUT, DELETE
    }

    /**
     * Execute an HTTP request synchronously.
     *
     * @param url          the full URL
     * @param method       the HTTP method
     * @param queryParams  query parameters (may be empty)
     * @param body         request body for POST/PUT (may be null)
     * @return the parsed response
     * @throws LatteSplashError if the request fails
     */
    LatteSplashResponse execute(String url, HttpMethod method, Map<String, String> queryParams, Object body)
            throws LatteSplashError;

    /**
     * Close any resources held by this client.
     */
    void close();
}
