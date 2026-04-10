package org.example.javacomponent.coreLogic.inputValidation;

import java.util.List;

public class InputValidator {
    /* implemented using abstraction */
    public boolean insertData(String inputValue, String fileName,
                           List bookErrorArrayLst, List studentErrorArrayLst, List transactionsErrorArrayLst,
                           int bookCounter, int studentCouter, int transactionsCounter){
        /* check filename and select appropriate method */
        boolean result = false;

        switch (fileName) {
            case "book.csv":
                result = new BookValidator().validate(inputValue, bookErrorArrayLst, bookCounter);
                break;
            case "students.csv":
                result = new StudentValidator().validate(inputValue, bookErrorArrayLst, bookCounter);
                break;
            case "transactions.csv":
                result = new TransactionsValidator().validate(inputValue, bookErrorArrayLst, bookCounter);
                break;
        }
        return result;
    }
}
