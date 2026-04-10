package org.example.javacomponent.coreLogic.inputValidation;

import org.example.javacomponent.coreLogic.IsbnFormat;

import java.util.List;

public class BookValidator {
    protected boolean validate(String inputValue, List errorArrayLst, int counter) {
        String[] splitPairs = errorArrayLst.get(counter).toString().split("="); // split key and value into an Array
        int index = Integer.parseInt(splitPairs[1]); // get the index position of the value with error

        if (inputValue.isEmpty()) {return false;}

        switch (index) {
            case 1:
                return IsbnFormat.validate(inputValue); // returns true or false
            case 3, 4:
                String[] splitValues = inputValue.split(" ");

                try {
                    //get copies and available values
                    int copies = Integer.parseInt(splitValues[0]);
                    int available =  Integer.parseInt(splitValues[1]);

                    if ((copies > 0 && copies <= 2) && (available > 0 && available <= copies)) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false; // return false if non-numerical characters found
                }
        }
        return false; // default return value
    }
}

