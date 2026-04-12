package org.example.javacomponent.corelogic.inputvalidation;

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
            case "student.csv":
                result = new StudentValidator().validate(inputValue, studentErrorArrayLst, studentCouter);
                break;
            case "transactions.csv":
                result = new TransactionsValidator().validate(inputValue, transactionsErrorArrayLst, transactionsCounter);
                break;
        }
        return result;
    }
}
