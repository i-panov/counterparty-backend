package ru.bellintegrator.practice.constraint;

import org.junit.*;

import static org.junit.Assert.*;

public class InnTest {
    private Inn.InnValidator validator = new  Inn.InnValidator();

    private boolean isValid(String value) {
        return validator.isValid(value, null);
    }

    @Test
    public void isValid() {
        assertFalse(isValid(null));
        assertFalse(isValid(""));
        assertFalse(isValid("123456789a"));
        assertFalse(isValid("1234567890"));
        assertFalse(isValid("123456789012"));
        assertTrue(isValid("6449013711"));
        assertTrue(isValid("500100732259"));
    }
}
