package org.example.javacomponent.coreLogic.dataModifer;

import java.util.List;

public class DataModifer {
    public void insertData(String inputValue, String fileName,
                       List bookErrorArrayLst, List studentErrorArrayLst, List transactionsErrorArrayLst,
                       int bookCounter, int studentCouter, int transactionsCounter){
        /* check filename and select appropriate method */

        switch (fileName) {
            case "book.csv":
                new BookModifer().modify(inputValue, fileName, bookErrorArrayLst, bookCounter);
                break;
            case "students.csv":
                new StudentModifer().modify(inputValue, fileName, studentErrorArrayLst, studentCouter);
                break;
            case "transactions.csv":
                new BookModifer().modify(inputValue, fileName, bookErrorArrayLst, bookCounter);
                break;
        }
    }
}
