package org.example.javacomponent.corelogic.inputvalidation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsValidatorTest {
    List<String> testData = new ArrayList<>();

    String correctFormat = "02/03/2025";
    String wrongDayFormat = "45/03/2025";
    String wrongMonthFormat = "01/14/2025";
    String wrongYearFormat = "01/14/20255";
    String missingSlash = "01 14 2025";

    @Test
    void correctTest() {
        /* test if correct date format returns true */
        TransactionsValidator transactionsValidator = new TransactionsValidator();
        assertTrue(transactionsValidator.validate(correctFormat, testData, 0));
    }

    @Test
    void wrongDayFormat() {
        /* test if wrong day format returns false */
        TransactionsValidator transactionsValidator = new TransactionsValidator();
        assertFalse(transactionsValidator.validate(wrongDayFormat, testData, 0));
    }

    @Test
    void wrongMonthFormat() {
        /* test if wrong month format returns false */
        TransactionsValidator transactionsValidator = new TransactionsValidator();
        assertFalse(transactionsValidator.validate(wrongMonthFormat, testData, 0));
    }

    @Test
    void wrongYearFormat() {
        /* test if wrong year format returns false */
        TransactionsValidator transactionsValidator = new TransactionsValidator();
        assertFalse(transactionsValidator.validate(wrongYearFormat, testData, 0));
    }

    @Test
    void missingSlash() {
        /* test if missing slash in date format returns false */
        TransactionsValidator transactionsValidator = new TransactionsValidator();
        assertFalse(transactionsValidator.validate(missingSlash, testData, 0));
    }
}