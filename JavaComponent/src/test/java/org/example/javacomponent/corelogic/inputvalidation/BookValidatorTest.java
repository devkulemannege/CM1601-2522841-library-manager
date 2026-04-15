package org.example.javacomponent.corelogic.inputvalidation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookValidatorTest {
    List<String> testData = new ArrayList<>(Arrays.asList("0=1","1=3"));

    // isbn test values
    String wrongIsbn = "9780141441140";
    String rightIsbn = "9780141441146";

    // copies/available test values
    String wrongCopiesAvailable = "2 3";
    String rightCopiesAvailable = "2 2";

    @Test
    void wrongIsbn() {
        /* test wrong isbn which returns false */
        BookValidator bookValidator = new BookValidator();
        boolean result = bookValidator.validate(wrongIsbn, testData, 0);
        assertEquals(false, result); // expected is false
    }

    @Test
    void rightIsbn(){
        /* test wrong isbn which returns true */
        BookValidator bookValidator = new BookValidator();
        boolean result = bookValidator.validate(rightIsbn, testData, 0);
        assertEquals(true, result); // expected is false
    }

    @Test
    void wrongCopiesAvailable() {
        /* test wrong no. of copies available which returns false */
        BookValidator bookValidator = new BookValidator();
        boolean result = bookValidator.validate(wrongCopiesAvailable, testData, 1);
        assertEquals(false, result);
    }

    @Test
    void rightCopiesAvailable() {
        /* test wrong no. of copies available which returns true */
        BookValidator bookValidator = new BookValidator();
        boolean result = bookValidator.validate(rightCopiesAvailable, testData, 1);
        assertEquals(true, result);
    }
}