package com.lattesplash;

import com.lattesplash.model.QueryParams;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LatteSplashResponseTest {

    @Test
    void getDataReturnsMap() {
        Map<String, Object> data = Map.of("key", "value");
        LatteSplashResponse response = new LatteSplashResponse(data);
        assertEquals("value", response.getData().get("key"));
    }

    @Test
    void getStringReturnsString() {
        LatteSplashResponse response = new LatteSplashResponse(Map.of("name", "test"));
        assertEquals("test", response.getString("name"));
    }

    @Test
    void getIntReturnsInteger() {
        LatteSplashResponse response = new LatteSplashResponse(Map.of("count", 42));
        assertEquals(42, response.getInt("count"));
    }

    @Test
    void getLongReturnsLong() {
        LatteSplashResponse response = new LatteSplashResponse(Map.of("id", 123456789L));
        assertEquals(123456789L, response.getLong("id"));
    }

    @Test
    void getDoubleReturnsDouble() {
        LatteSplashResponse response = new LatteSplashResponse(Map.of("score", 3.14));
        assertEquals(3.14, response.getDouble("score"), 0.001);
    }

    @Test
    void getBooleanReturnsBoolean() {
        LatteSplashResponse response = new LatteSplashResponse(Map.of("active", true));
        assertTrue(response.getBoolean("active"));
    }

    @Test
    void getMapReturnsMap() {
        @SuppressWarnings("unchecked")
        Map<String, Object> nested = Map.of("a", 1);
        LatteSplashResponse response = new LatteSplashResponse(Map.of("data", nested));
        assertNotNull(response.getMap("data"));
        assertEquals(1, response.getMap("data").get("a"));
    }

    @Test
    void getListReturnsList() {
        LatteSplashResponse response = new LatteSplashResponse(Map.of("items", java.util.List.of(1, 2, 3)));
        assertNotNull(response.getList("items"));
        assertEquals(3, response.getList("items").size());
    }

    @Test
    void containsKeyWorks() {
        LatteSplashResponse response = new LatteSplashResponse(Map.of("a", 1));
        assertTrue(response.containsKey("a"));
        assertFalse(response.containsKey("b"));
    }

    @Test
    void isEmptyWorks() {
        assertTrue(new LatteSplashResponse(Map.of()).isEmpty());
        assertFalse(new LatteSplashResponse(Map.of("a", 1)).isEmpty());
    }

    @Test
    void nullMapCreatesEmptyResponse() {
        LatteSplashResponse response = new LatteSplashResponse(null);
        assertTrue(response.isEmpty());
    }

    @Test
    void getReturnsNullForMissingKey() {
        LatteSplashResponse response = new LatteSplashResponse(Map.of());
        assertNull(response.get("missing"));
        assertNull(response.getString("missing"));
        assertNull(response.getInt("missing"));
    }
}
