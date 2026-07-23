package com.lattesplash.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QueryParamsTest {

    @Test
    void putStringAddsParam() {
        Map<String, String> params = new QueryParams().put("key", "value").build();
        assertEquals("value", params.get("key"));
    }

    @Test
    void putIntAddsParam() {
        Map<String, String> params = new QueryParams().put("count", 10).build();
        assertEquals("10", params.get("count"));
    }

    @Test
    void putBooleanAddsParam() {
        Map<String, String> params = new QueryParams().put("flag", true).build();
        assertEquals("true", params.get("flag"));
    }

    @Test
    void putNullValueSkipsParam() {
        Map<String, String> params = new QueryParams().put("key", (Object) null).build();
        assertFalse(params.containsKey("key"));
    }

    @Test
    void putEmptyStringSkipsParam() {
        Map<String, String> params = new QueryParams().put("key", "").build();
        assertFalse(params.containsKey("key"));
    }

    @Test
    void putNullKeySkipsParam() {
        Map<String, String> params = new QueryParams().put(null, "value").build();
        assertTrue(params.isEmpty());
    }

    @Test
    void isEmptyWorks() {
        QueryParams qp = new QueryParams();
        assertTrue(qp.isEmpty());
        qp.put("k", "v");
        assertFalse(qp.isEmpty());
    }

    @Test
    void buildReturnsCopy() {
        QueryParams qp = new QueryParams().put("k", "v");
        Map<String, String> params1 = qp.build();
        Map<String, String> params2 = qp.build();
        assertEquals(params1, params2);
        assertNotSame(params1, params2);
    }
}
