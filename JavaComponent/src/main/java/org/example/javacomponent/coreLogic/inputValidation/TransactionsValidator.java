package org.example.javacomponent.corelogic.inputvalidation;

import java.util.List;

class TransactionsValidator {
    protected boolean validate(String inputValue, List errorArrayLst, int counter) {

        if (!(inputValue.contains("/"))){
            // return false if input does not have / required for proper format
            return false;
        }

        int day = 0;
        int month = 0;
        int year = 0;
        try {
            String[] splitInput = inputValue.split("/"); // split and get date values separately
            day = Integer.parseInt(splitInput[0]);
            month = Integer.parseInt(splitInput[1]);
            year = Integer.parseInt(splitInput[2]);
        } catch (Exception e) {
            return false;
        }

        if ((0 < day && day < 32) && (0 < month && month < 13) && (1999 < year && Integer.toString(year).length()<5)) {
            return true;
        }
        return false;
    }
}
