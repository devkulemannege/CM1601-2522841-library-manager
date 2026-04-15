package org.example.javacomponent.corelogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsbnFormatTest {
    // isbn test values
    String wrongIsbn = "9780141441140";
    String rightIsbn = "9780141441146";

    @Test
    void testIsbnFormatFalse() {
        /* returns false if isbn is wrong */
        assertFalse(IsbnFormat.validate(wrongIsbn));
    }

    @Test
    void testIsbnFormatTrue() {
        /* returns true if isbn is wrong */
        assertTrue(IsbnFormat.validate(rightIsbn));
    }
}