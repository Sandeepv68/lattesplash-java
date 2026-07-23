package com.lattesplash;

/**
 * Exception thrown by LatteSplash API operations.
 *
 * <p>Contains optional HTTP status information when the error originates from the Unsplash API.</p>
 */
public class LatteSplashError extends Exception {

    private final Integer statusCode;
    private final String statusText;

    public LatteSplashError(String message) {
        this(message, null, null, null);
    }

    public LatteSplashError(String message, Throwable cause) {
        this(message, null, null, cause);
    }

    public LatteSplashError(String message, Integer statusCode, String statusText) {
        this(message, statusCode, statusText, null);
    }

    public LatteSplashError(String message, Integer statusCode, String statusText, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public boolean hasStatusCode() {
        return statusCode != null;
    }
}
