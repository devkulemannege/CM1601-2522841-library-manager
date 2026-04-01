package org.example.javacomponent.coreLogic;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.*;

public class DataExtractor {
    private String directory;
    private HashMap<Integer, Integer> bookErrorTracker = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> studentErrorTracker = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> transactionsErrorTracker = new HashMap<Integer, Integer>();

    public DataExtractor(String directory){
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


            for (Integer key : bookErrorTracker.keySet()){
                System.out.printf("%s %s\n", key, bookErrorTracker.get(key));
            }

            /*
            for (String[] data : allData) {
                System.out.println(Arrays.toString(data));
            }*/
        }
    }
}
