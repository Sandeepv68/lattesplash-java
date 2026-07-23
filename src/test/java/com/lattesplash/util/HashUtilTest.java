package com.lattesplash.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HashUtilTest {

    @Test
    void sha256ProducesCorrectHash() {
        String hash = HashUtil.sha256("test");
        assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", hash);
    }

    @Test
    void sha256ProducesDifferentHashesForDifferentInputs() {
        String hash1 = HashUtil.sha256("hello");
        String hash2 = HashUtil.sha256("world");
        assertNotEquals(hash1, hash2);
    }

    @Test
    void sha256Produces64CharacterHex() {
        String hash = HashUtil.sha256("anything");
        assertEquals(64, hash.length());
    }

    @Test
    void sha256ThrowsOnNullInput() {
        assertThrows(IllegalArgumentException.class, () -> HashUtil.sha256(null));
    }

    @Test
    void sha256HandlesEmptyString() {
        String hash = HashUtil.sha256("");
        assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", hash);
    }
}
