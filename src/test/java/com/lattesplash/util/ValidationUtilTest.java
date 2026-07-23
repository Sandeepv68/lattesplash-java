package com.lattesplash.util;

import com.lattesplash.LatteSplashError;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationUtilTest {

    @Test
    void requireNonEmptyPassesForValidValue() throws LatteSplashError {
        ValidationUtil.requireNonEmpty("hello", "name");
    }

    @Test
    void requireNonEmptyThrowsForNull() {
        LatteSplashError error = assertThrows(LatteSplashError.class,
                () -> ValidationUtil.requireNonEmpty((String) null, "username"));
        assertEquals("Parameter : username is required and cannot be empty!", error.getMessage());
    }

    @Test
    void requireNonEmptyThrowsForEmptyString() {
        LatteSplashError error = assertThrows(LatteSplashError.class,
                () -> ValidationUtil.requireNonEmpty("", "username"));
        assertEquals("Parameter : username is required and cannot be empty!", error.getMessage());
    }

    @Test
    void requireNonEmptyIdHasSpecialMessage() {
        LatteSplashError error = assertThrows(LatteSplashError.class,
                () -> ValidationUtil.requireNonEmpty("", "id"));
        assertEquals("Parameter : id is required!", error.getMessage());
    }

    @Test
    void requireNonEmptyQueryHasSpecialMessage() {
        LatteSplashError error = assertThrows(LatteSplashError.class,
                () -> ValidationUtil.requireNonEmpty("", "query"));
        assertEquals("Parameter : query is missing!", error.getMessage());
    }

    @Test
    void requireNonEmptyObjectThrowsForNull() {
        LatteSplashError error = assertThrows(LatteSplashError.class,
                () -> ValidationUtil.requireNonEmpty((Object) null, "field"));
        assertEquals("Parameter : field is required and cannot be empty!", error.getMessage());
    }

    @Test
    void requireSupportedValuePassesForAllowedValue() throws LatteSplashError {
        List<String> allowed = Arrays.asList("a", "b", "c");
        ValidationUtil.requireSupportedValue("a", allowed, "field");
    }

    @Test
    void requireSupportedValuePassesForNull() throws LatteSplashError {
        List<String> allowed = Arrays.asList("a", "b", "c");
        ValidationUtil.requireSupportedValue(null, allowed, "field");
    }

    @Test
    void requireSupportedValueThrowsForUnsupported() {
        List<String> allowed = Arrays.asList("a", "b", "c");
        LatteSplashError error = assertThrows(LatteSplashError.class,
                () -> ValidationUtil.requireSupportedValue("d", allowed, "order_by"));
        assertEquals("Parameter : order_by has an unsupported value!", error.getMessage());
    }
}
