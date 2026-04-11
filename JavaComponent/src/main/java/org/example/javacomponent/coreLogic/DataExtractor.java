package org.example.javacomponent.coreLogic;

import com.opencsv.CSVReader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import org.example.javacomponent.AppVariables;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataExtractor {
    private HashMap<Integer, Integer> bookErrorTracker = new HashMap<Integer, Integer>();
    private HashMap<Integer, String> studentErrorTracker = new HashMap<Integer, String>();
    private HashMap<Integer, String> transactionsErrorTracker = new HashMap<Integer, String>();

    // getters for HashMaps class variables
    public HashMap<Integer, Integer> getBookErrorTracker() {
        return bookErrorTracker;
    }

    public HashMap<Integer, String> getStudentErrorTracker() {
        return studentErrorTracker;
    }

    public HashMap<Integer, String> getTransactionsErrorTracker() {
        return transactionsErrorTracker;
    }

    public void mainProcess() {
        String[] fileNames = {"book.csv","student.csv","transactions.csv"};

        // loop through all csv files
        for (String fileName : fileNames){
            // read csv file using OpenCSV

            List<String[]> csvData = null; // create List interface
            try {
                CSVReader reader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), fileName)));
                csvData = reader.readAll();
                csvData.removeFirst(); // remove column headers
            } catch (FileNotFoundException e) {
                Alerts.showCriticalError("Missing CSV Files.");
            } catch (Exception e) {
                Alerts.showCriticalError(e.toString());
            }

            // loop through elements on of individual rows
            for (int i = 0; i< csvData.size(); i++){
                if (fileName.equals("book.csv")) {
                    // validate ISBN check digits
                    String isbn = csvData.get(i)[1].toString(); // get isbn to a variable
                    try {
                        if (!(IsbnFormat.validate(isbn))) { // validate using custom method
                            bookErrorTracker.put(i + 2, 1); // record error location using row & element index
                        }
                        // validate no. of copies of books
                        if (!(Integer.parseInt(csvData.get(i)[3]) > 0 && Integer.parseInt(csvData.get(i)[3]) <= 2)) {
                            bookErrorTracker.put(i + 2, 3); // record error location using row & element index
                        }
                        // validate no. of available books
                        if (!(Integer.parseInt(csvData.get(i)[4]) > 0
                                && Integer.parseInt(csvData.get(i)[4]) <= Integer.parseInt(csvData.get(i)[3]))) {
                            bookErrorTracker.put(i + 2, 4); // record error location using row & element index
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Alerts.showCriticalError(String.format("CSV file contains invalid data at row %s", i));
                    } catch (NumberFormatException e) {
                        Alerts.showCriticalError(String.format("CSV file contains invalid data at row %s", i));
                    } catch (Exception e) {
                        Alerts.showCriticalError(e.toString());
                    }

                } else if (fileName.equals("student.csv")) {
                    // validate student ID
                    try {
                        int whileCounterTracker = 0, duplicateCounter = 0;
                        String currentStudentId = csvData.get(i)[0]; // get the current student ID

                        // loop and find if any duplicates exists
                        while (whileCounterTracker < csvData.size()) {
                            String whileIterateId = csvData.get(whileCounterTracker)[0];

                            if (whileIterateId.equals(currentStudentId)) {
                                duplicateCounter++;
                            }
                            if ((whileIterateId.length() != 8)
                                    || !(whileIterateId.matches("[0-9]*"))) {
                                studentErrorTracker.put(whileCounterTracker+2, "invalid"); // record student ID error type and row
                            }
                            if (duplicateCounter == 2) {
                                studentErrorTracker.put(whileCounterTracker+2, "duplicate"); // record student ID error type and row
                                break;
                            }
                            whileCounterTracker++;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Alerts.showCriticalError(String.format("CSV file contains invalid data at row %s", i));
                    } catch (NumberFormatException e) {
                        Alerts.showCriticalError(String.format("CSV file contains invalid data at row %s", i));
                    } catch (Exception e) {
                        Alerts.showCriticalError(e.toString());
                    }

                } else if (fileName.equals("transactions.csv")) {
                    // validate date information in transactions
                    String[] splitDate = csvData.get(i)[0].split("/"); // split date values by /

                    try { // check if ranges are correct for dd/mm/yyyy
                        int day = Integer.parseInt(splitDate[0]);
                        int month = Integer.parseInt(splitDate[1]);
                        int year = Integer.parseInt(splitDate[2]);

                        if (!(0 < day && day < 32)) {
                            transactionsErrorTracker.put(i+2, "day");
                        }
                        if (!(0 < month && month < 13)) {
                            transactionsErrorTracker.put(i+2, "month");
                        }
                        if (!(1999 < year)) {
                            transactionsErrorTracker.put(i+2, "year");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // record as error if date is missing the day, month or year
                        transactionsErrorTracker.put(i+2, "invalid");
                    } catch (NumberFormatException e) {
                        transactionsErrorTracker.put(i+2, "invalid");
                    } catch (Exception e) {
                        Alerts.showCriticalError(e.toString());
                    }
                }
            }
        }
    }
}
