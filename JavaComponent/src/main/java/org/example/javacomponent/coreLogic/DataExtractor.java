package org.example.javacomponent.coreLogic;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;

public class DataExtractor {
    private String directory;
    private HashMap<Integer, Integer> bookErrorTracker = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> studentErrorTracker = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> transactionsErrorTracker = new HashMap<Integer, Integer>();

    public DataExtractor(String directory){
        /* contractor method */
        this.directory = directory;
    }

    public void mainProcess() throws Exception{
        String[] fileNames = {"book.csv","student.csv","transactions.csv"};

        // loop through all csv files
        for (String fileName : fileNames){
            FileReader fileReader = new FileReader(String.format("%s\\%s", directory, fileName));
            CSVReader reader = new CSVReader(fileReader); // read csv file using OpenCSV

            List<String[]> allData = reader.readAll(); // create List interface
            allData.removeFirst(); // remove column headers

            // loop through elements on of individual rows
            for (int i=0; i<allData.size(); i++){
                if (fileName.equals("book.csv")){
                    // validate ISBN check digits
                    int checkDigit = 0, actualCheckDigit = 0;
                    String[] isbnCharacters = (allData.get(i)[1].toString()).split("");
                    actualCheckDigit = Integer.parseInt(isbnCharacters[isbnCharacters.length-1]);

                    for (int pos=0; pos<isbnCharacters.length-1; pos++){
                        System.out.println(isbnCharacters[pos]);
                        if ((pos % 2) == 1){
                            checkDigit +=  Integer.parseInt(isbnCharacters[pos]) * 3;
                        } else {
                            checkDigit +=  Integer.parseInt(isbnCharacters[pos]);
                        }
                    }
                    checkDigit = checkDigit % 10; // remainder
                    checkDigit = (10 - checkDigit) % 10;

                    if (checkDigit != actualCheckDigit){
                        bookErrorTracker.put(i, 1); // record error location using row & element index
                    }

                    // validate no. of copies of books
                    if (Integer.parseInt(allData.get(i)[3]) > 2){
                        bookErrorTracker.put(i, 3);
                    }

                    // validate no. of available books
                    if ((Integer.parseInt(allData.get(i)[4]) < 0) || (Integer.parseInt(allData.get(i)[4]) > Integer.parseInt(allData.get(i)[3]))) {
                        bookErrorTracker.put(i, 4);
                    }
                }
            }
        }
    }
}
