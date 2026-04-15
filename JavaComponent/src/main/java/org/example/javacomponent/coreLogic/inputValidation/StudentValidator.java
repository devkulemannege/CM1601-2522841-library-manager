package org.example.javacomponent.corelogic.inputvalidation;

import com.opencsv.CSVReader;
import org.example.javacomponent.AppVariables;
import org.example.javacomponent.corelogic.Alerts;

import java.io.FileReader;
import java.util.List;

class StudentValidator {
    protected boolean validate(String inputValue, List errorArrayLst, int counter) {
        String[] splitPairs = errorArrayLst.get(counter).toString().split("="); // split key and value into an Array
        int row = Integer.parseInt(splitPairs[0])-2; // get the row number where the error is present.
                                                     // -2 because starts from 0, first row is removed
        int whileCounterTracker = 0, duplicateCounter = 0;

        try {
            CSVReader reader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), "student.csv")));
            List<String[]> csvData = reader.readAll(); // create List interface
            csvData.removeFirst();

            // loop and find if any duplicates exists
            while (whileCounterTracker < csvData.size()) {
                String whileIterateId = csvData.get(whileCounterTracker)[0];

                if (whileIterateId.equals(inputValue)) {
                    duplicateCounter++;
                }

                if (duplicateCounter > 0) {
                    Alerts.duplicateStudentInput(); // show duplicate value warning
                    return false;
                }
                whileCounterTracker++;
            }

            if ((inputValue.length() != 8) || !(inputValue.matches("[0-9]*"))) {
                return false;
            }
        } catch (Exception e) {
            Alerts.showCriticalError(e.toString()); // handle error if critical error arises
        }
        return true; // return true if no errors are found
    }
}
