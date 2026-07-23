package com.lattesplash.api;

import com.lattesplash.LatteSplashError;
import com.lattesplash.LatteSplashResponse;
import com.lattesplash.http.HttpClient;
import com.lattesplash.util.UrlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Unsplash Stats API endpoints.
 */
public class StatsApi {

    private static final Logger log = LoggerFactory.getLogger(StatsApi.class);

    private final HttpClient httpClient;
    private final HttpClient asyncClient;

    public StatsApi(HttpClient httpClient, HttpClient asyncClient) {
        this.httpClient = httpClient;
        this.asyncClient = asyncClient;
    }

    public LatteSplashResponse getStatsTotals() throws LatteSplashError {
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("STATS_TOTALS", null),
                HttpClient.HttpMethod.GET, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> getStatsTotalsAsync() {
        try {
            return CompletableFuture.completedFuture(getStatsTotals());
        } catch (LatteSplashError e) {
            log.debug("getStatsTotalsAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    public LatteSplashResponse getStatsMonth() throws LatteSplashError {
        return httpClient.execute(UrlConfig.getInstance().resolveUrl("STATS_MONTH", null),
                HttpClient.HttpMethod.GET, Map.of(), null);
    }

    public CompletableFuture<LatteSplashResponse> getStatsMonthAsync() {
        try {
            return CompletableFuture.completedFuture(getStatsMonth());
        } catch (LatteSplashError e) {
            log.debug("getStatsMonthAsync failed: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}
