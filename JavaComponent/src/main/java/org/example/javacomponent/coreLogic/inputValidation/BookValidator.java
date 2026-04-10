package org.example.javacomponent.coreLogic.inputValidation;

import java.util.List;

public class BookValidator extends InputValidator {
    @Override
    public boolean validate(String inputValue, List errorArrayLst, int counter) {
        String[] splitPairs = errorArrayLst.get(counter).toString().split("="); // split key and value into an Array
        int index = Integer.parseInt(splitPairs[1]); // get the index position of the value with error

        if  (inputValue.isEmpty()) {
            return false;
        } else if (index == 1) {
            // todo: isbn validator
            return true;
        } else if (index == 3 || index == 4) {
            // todo: copies and available validator
            return true;
        } else {
            return false;
        }
    }
}

