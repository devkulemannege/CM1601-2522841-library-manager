package org.example.javacomponent.coreLogic.inputValidation;

import java.util.List;

public class TransactionsValidator {
    protected boolean validate(String inputValue, List errorArrayLst, int counter) {

        if (!(inputValue.contains("/"))){
            // return false if input does not have / required for proper format
            return false;
        }

        String[] splitInput = inputValue.split("/"); // split and get date values separately
        int day = Integer.parseInt(splitInput[0]);
        int month = Integer.parseInt(splitInput[1]);
        int year = Integer.parseInt(splitInput[2]);

        if ((0 < day && day < 32) && (0 < month && month < 13) && (1999 < year && Integer.toString(year).length()<5)) {
            return true;
        }
        return false;
    }
}
