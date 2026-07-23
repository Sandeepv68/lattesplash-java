package com.lattesplash.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lattesplash.LatteSplashError;
import com.lattesplash.LatteSplashResponse;
import com.lattesplash.auth.AuthProvider;
import com.lattesplash.model.LatteSplashConfig;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp-based implementation of {@link HttpClient}.
 */
public class OkHttpClientAdapter implements HttpClient {

    private static final Logger log = LoggerFactory.getLogger(OkHttpClientAdapter.class);
    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
    private static final Gson GSON = new Gson();

    private final OkHttpClient client;
    private final AuthProvider authProvider;

    public OkHttpClientAdapter(LatteSplashConfig config, AuthProvider authProvider) {
        this.authProvider = authProvider;

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(config.getTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(config.getTimeout(), TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .addInterceptor(new RetryInterceptor(config.getRetries(), config.getRetryDelayMs()));

        this.client = builder.build();
    }

    @Override
    public LatteSplashResponse execute(String url, HttpMethod method, Map<String, String> queryParams, Object body)
            throws LatteSplashError {
        try {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(buildUrl(url, queryParams))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Requested-With", "LatteSplash");

            for (Map.Entry<String, String> header : authProvider.getHeaders().entrySet()) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }

            switch (method) {
                case GET:
                    requestBuilder.get();
                    break;
                case POST:
                    requestBuilder.post(toRequestBody(body));
                    break;
                case PUT:
                    requestBuilder.put(toRequestBody(body));
                    break;
                case DELETE:
                    requestBuilder.delete(toRequestBody(body));
                    break;
            }

            log.debug("{} {} {}", method, url, queryParams != null && !queryParams.isEmpty() ? queryParams : "");

            try (Response response = client.newCall(requestBuilder.build()).execute()) {
                return handleResponse(response);
            }

        } catch (LatteSplashError e) {
            throw e;
        } catch (IOException e) {
            log.error("HTTP request failed: {} {} - {}", method, url, e.getMessage());
            throw createError(e);
        }
    }

    /**
     * Execute an HTTP request asynchronously using OkHttp's non-blocking enqueue.
     */
    public CompletableFuture<LatteSplashResponse> executeAsync(String url, HttpMethod method,
                                                                Map<String, String> queryParams, Object body) {
        CompletableFuture<LatteSplashResponse> future = new CompletableFuture<>();

        try {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(buildUrl(url, queryParams))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Requested-With", "LatteSplash");

            for (Map.Entry<String, String> header : authProvider.getHeaders().entrySet()) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }

            switch (method) {
                case GET:
                    requestBuilder.get();
                    break;
                case POST:
                    requestBuilder.post(toRequestBody(body));
                    break;
                case PUT:
                    requestBuilder.put(toRequestBody(body));
                    break;
                case DELETE:
                    requestBuilder.delete(toRequestBody(body));
                    break;
            }

            log.debug("Async {} {} {}", method, url, queryParams != null && !queryParams.isEmpty() ? queryParams : "");

            client.newCall(requestBuilder.build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    log.error("Async HTTP request failed: {} {} - {}", method, url, e.getMessage());
                    future.completeExceptionally(createError(e));
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        future.complete(handleResponse(response));
                    } catch (LatteSplashError e) {
                        future.completeExceptionally(e);
                    }
                }
            });
        } catch (Exception e) {
            future.completeExceptionally(e instanceof LatteSplashError ? (LatteSplashError) e : createError((IOException) e));
        }

        return future;
    }

    private LatteSplashResponse handleResponse(Response response) throws LatteSplashError {
        int code = response.code();

        if (code == 204) {
            return new LatteSplashResponse(Map.of(
                    "status", code,
                    "statusText", "No Content",
                    "message", "Content Deleted"
            ));
        }

        if (code == 403) {
            return new LatteSplashResponse(Map.of(
                    "status", code,
                    "statusText", "Forbidden",
                    "message", "Rate Limit Exceeded"
            ));
        }

        String responseBody;
        try {
            responseBody = response.body() != null ? response.body().string() : "{}";
        } catch (IOException e) {
            throw createError(e);
        }

        if (!response.isSuccessful()) {
            String statusText = response.message();
            log.warn("API error: {} {} - {}", code, statusText, responseBody);
            throw new LatteSplashError(
                    "Request failed with status " + code + ": " + statusText,
                    code, statusText, null
            );
        }

        try {
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            Map<String, Object> data = GSON.fromJson(json, Map.class);
            return new LatteSplashResponse(data);
        } catch (Exception e) {
            log.warn("Failed to parse JSON response, returning raw body: {}", e.getMessage());
            return new LatteSplashResponse(Map.of("raw", responseBody));
        }
    }

    private String buildUrl(String url, Map<String, String> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        sb.append(url.contains("?") ? "&" : "?");

        boolean first = true;
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (!first) {
                sb.append("&");
            }
            sb.append(urlEncode(entry.getKey()));
            sb.append("=");
            sb.append(urlEncode(entry.getValue()));
            first = false;
        }

        return sb.toString();
    }

    private String urlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            log.warn("UTF-8 encoding not available, returning raw value");
            return value;
        }
    }

    private RequestBody toRequestBody(Object body) {
        if (body == null) {
            return RequestBody.create(new byte[0]);
        }
        String json = GSON.toJson(body);
        return RequestBody.create(json, JSON_MEDIA_TYPE);
    }

    private LatteSplashError createError(IOException e) {
        return new LatteSplashError("Request failed: " + e.getMessage(), e);
    }

    @Override
    public void close() {
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }
}
