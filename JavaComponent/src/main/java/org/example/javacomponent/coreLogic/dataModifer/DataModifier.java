package org.example.javacomponent.corelogic.datamodifer;

import java.util.List;

public class DataModifier {
    public void insertData(String inputValue, String fileName,
                       List bookErrorArrayLst, List studentErrorArrayLst, List transactionsErrorArrayLst,
                       int bookCounter, int studentCounter, int transactionsCounter){
        /* check filename and select appropriate method */

        switch (fileName) {
            case "book.csv":
                new BookModifier().modify(inputValue, fileName, bookErrorArrayLst, bookCounter);
                break;
            case "student.csv":
                new StudentModifier().modify(inputValue, fileName, studentErrorArrayLst, studentCounter);
                break;
            case "transactions.csv":
                new TransactionsModifier().modify(inputValue, fileName, transactionsErrorArrayLst, transactionsCounter);
                break;
        }
    }
}
