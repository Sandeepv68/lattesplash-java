package com.lattesplash.util;

import com.lattesplash.LatteSplashError;

import java.util.List;

/**
 * Input validation helpers.
 */
public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static void requireNonEmpty(String value, String fieldName) throws LatteSplashError {
        if (value == null || value.isEmpty()) {
            String message;
            if ("id".equals(fieldName)) {
                message = "Parameter : id is required!";
            } else if ("query".equals(fieldName)) {
                message = "Parameter : query is missing!";
            } else {
                message = "Parameter : " + fieldName + " is required and cannot be empty!";
            }
            throw new LatteSplashError(message);
        }
    }

    public static void requireNonEmpty(Object value, String fieldName) throws LatteSplashError {
        if (value == null) {
            throw new LatteSplashError("Parameter : " + fieldName + " is required and cannot be empty!");
        }
    }

    public static void requireSupportedValue(String value, List<String> allowedValues, String fieldName)
            throws LatteSplashError {
        if (value != null && !allowedValues.contains(value)) {
            throw new LatteSplashError("Parameter : " + fieldName + " has an unsupported value!");
        }
    }
}
