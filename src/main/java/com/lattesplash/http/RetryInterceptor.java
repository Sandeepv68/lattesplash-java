package com.lattesplash.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * OkHttp interceptor that retries failed requests up to a configured maximum.
 *
 * <p>Retries on 5xx server errors and network failures with a configurable delay.</p>
 */
public class RetryInterceptor implements Interceptor {

    private final int maxRetries;
    private final long retryDelayMs;

    public RetryInterceptor(int maxRetries, long retryDelayMs) {
        this.maxRetries = maxRetries;
        this.retryDelayMs = retryDelayMs;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        IOException lastException = null;

        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            try {
                Response response = chain.proceed(request);

                if (response.isSuccessful() || attempt >= maxRetries) {
                    return response;
                }

                response.close();

                if (retryDelayMs > 0) {
                    sleep(retryDelayMs);
                }
            } catch (IOException e) {
                lastException = e;
                if (attempt >= maxRetries) {
                    throw e;
                }
                if (retryDelayMs > 0) {
                    sleep(retryDelayMs);
                }
            }
        }

        throw lastException != null ? lastException : new IOException("Request failed after " + maxRetries + " retries");
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Retry sleep interrupted", e);
        }
    }
}
