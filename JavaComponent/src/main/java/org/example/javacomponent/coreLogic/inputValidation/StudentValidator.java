package org.example.javacomponent.coreLogic.inputValidation;

import java.util.List;

public class StudentValidator extends InputValidator {
    @Override
    public boolean validate(String inputValue, List errorArrayLst, int counter) {
        String[] splitPairs = errorArrayLst.get(counter).toString().split("="); // split key and value into an Array
        int index = Integer.parseInt(splitPairs[1]); // get the index position of the value with error

        return false; // todo: complete this
    }
}
