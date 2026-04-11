package org.example.javacomponent.coreLogic.inputValidation;

import org.example.javacomponent.coreLogic.Alerts;

import java.util.List;

public class StudentValidator {
    protected boolean validate(String inputValue, List errorArrayLst, int counter) {
        String[] splitPairs = errorArrayLst.get(counter).toString().split("="); // split key and value into an Array
        String type = splitPairs[1]; // get the index position of the value with error

        try {
            // todo: complete code
        } catch (Exception e) {
            Alerts.showCriticalError(e.toString()); // handle error if critical error arises
        }

        return true;
    }
}
