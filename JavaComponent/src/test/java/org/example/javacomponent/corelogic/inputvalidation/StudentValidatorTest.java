package org.example.javacomponent.corelogic.inputvalidation;

import org.example.javacomponent.corelogic.Alerts;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentValidatorTest {
    List<String> testData = new ArrayList<>(Arrays.asList(
            "0=duplicate",
            "1=invalid"
    ));

    String testValue = "20241928";

    @Test
    void correctValue() {
        StudentValidator studentValidator = new StudentValidator();

        // try to ignore when duplicateStudentInput method is executed
        try (MockedStatic<Alerts> mockedAlerts = Mockito.mockStatic(Alerts.class)) {
            mockedAlerts.when(Alerts::duplicateStudentInput).thenAnswer(Mockito.CALLS_REAL_METHODS);

            boolean result = studentValidator.validate(testValue, testData, 0);
            assertTrue(result); // duplicate found should return false
        }
    }
}