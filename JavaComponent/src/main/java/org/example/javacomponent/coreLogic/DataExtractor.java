package org.example.javacomponent.coreLogic;

import com.opencsv.CSVReader;
import javafx.scene.control.Alert;

import java.io.FileReader;
import java.util.*;

public class DataExtractor {
    private String directory;
    private HashMap<Integer, Integer> bookErrorTracker = new HashMap<Integer, Integer>();
    private HashMap<String, Integer> studentErrorTracker = new HashMap<String, Integer>();
    private HashMap<String, Integer> transactionsErrorTracker = new HashMap<String, Integer>();

    public DataExtractor(String directory){
        /* contractor method */
        this.directory = directory;
    }

    // getters for HashMaps class variables
    public HashMap<Integer, Integer> getBookErrorTracker() {
        return bookErrorTracker;
    }

    public HashMap<String, Integer> getStudentErrorTracker() {
        return studentErrorTracker;
    }

    public HashMap<String, Integer> getTransactionsErrorTracker() {
        return transactionsErrorTracker;
    }

    void showCriticalError(String msg) {
        /* reusable method to show error box */
        Alert alert = new Alert(Alert.AlertType.ERROR); // show an error box if files not found
        alert.setContentText(String.format("An error occurred trying to read CSV file: \n%s", msg));
        alert.setHeaderText(null);
        alert.showAndWait();
        System.exit(0); // close after critical error
    }

    public void mainProcess() throws Exception{
        String[] fileNames = {"book.csv","student.csv","transactions.csv"};

        // loop through all csv files
        for (String fileName : fileNames){
            FileReader fileReader = new FileReader(String.format("%s\\%s", directory, fileName));
            CSVReader reader = new CSVReader(fileReader); // read csv file using OpenCSV

            List<String[]> csvData = reader.readAll(); // create List interface
            csvData.removeFirst(); // remove column headers

            // loop through elements on of individual rows
            for (int i = 0; i< csvData.size(); i++){
                if (fileName.equals("book.csv")) {
                    // validate ISBN check digits
                    try {
                        int checkDigit = 0, actualCheckDigit = 0;
                        String[] isbnCharacters = (csvData.get(i)[1].toString()).split("");
                        actualCheckDigit = Integer.parseInt(isbnCharacters[isbnCharacters.length - 1]);

                        for (int pos = 0; pos < isbnCharacters.length - 1; pos++) {
                            if ((pos % 2) == 1) {
                                checkDigit += Integer.parseInt(isbnCharacters[pos]) * 3;
                            } else {
                                checkDigit += Integer.parseInt(isbnCharacters[pos]);
                            }
                        }
                        checkDigit = checkDigit % 10; // remainder
                        checkDigit = (10 - checkDigit) % 10;
                        if (checkDigit != actualCheckDigit) {
                            bookErrorTracker.put(i, 1); // record error location using row & element index
                        }
                        // validate no. of copies of books
                        if ((Integer.parseInt(csvData.get(i)[3]) < 0)
                                || (Integer.parseInt(csvData.get(i)[3]) > 2)) {
                            bookErrorTracker.put(i, 3); // record error location using row & element index
                        }
                        // validate no. of available books
                        if ((Integer.parseInt(csvData.get(i)[4]) < 0)
                                || (Integer.parseInt(csvData.get(i)[4]) > Integer.parseInt(csvData.get(i)[3]))) {
                            bookErrorTracker.put(i, 4); // record error location using row & element index
                        }
                    } catch (Exception e) {
                        showCriticalError(e.getMessage());
                    }

                } else if (fileName.equals("student.csv")) {
                    // validate student ID
                    try {
                        int whileCounterTracker = 0, duplicateCounter = 0;
                        int currentStudentId = Integer.parseInt(csvData.get(i)[0]); // get the current student ID

                        // loop and find if any duplicates exists
                        while (whileCounterTracker < csvData.size()) {
                            int whileIterateId = Integer.parseInt(csvData.get(whileCounterTracker)[0]);

                            if (whileIterateId == currentStudentId) {
                                duplicateCounter++;
                            }
                            if ((Integer.toString(whileIterateId).length() != 8)
                                    || !(Integer.toString(whileIterateId).matches("[0-9]*"))) {
                                studentErrorTracker.put("invalid", whileIterateId); // record student ID error type and row
                            }
                            if (duplicateCounter == 2) {
                                studentErrorTracker.put("duplicate", whileCounterTracker); // record student ID error type and row
                                break;
                            }
                            whileCounterTracker++;
                        }
                    } catch (Exception e) {
                        showCriticalError(e.getMessage());
                    }

                } else if (fileName.equals("transactions.csv")) {
                    // validate date information in transactions
                    String[] splitDate = csvData.get(i)[0].split("/"); // split date values by /

                    try { // check if ranges are correct for dd/mm/yyyy
                        int day = Integer.parseInt(splitDate[0]);
                        int month = Integer.parseInt(splitDate[1]);
                        int year = Integer.parseInt(splitDate[2]);

                        if (!(0 < day && day < 32)) {
                            transactionsErrorTracker.put("day", i);
                        }
                        if (!(0 < month && month < 13)) {
                            transactionsErrorTracker.put("month", i);
                        }
                        if (!(1999 < year)) {
                            transactionsErrorTracker.put("year", i);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // record as error if date is missing the day, month or year
                        transactionsErrorTracker.put("invalid", i);
                    } catch (NumberFormatException e) {
                        transactionsErrorTracker.put("invalid", i);
                    } catch (Exception e) {
                        showCriticalError(e.getMessage());
                    }
                }
            }
        }
    }
}
