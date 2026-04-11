package org.example.javacomponent.coreLogic.dataModifer;

import com.opencsv.CSVReader;
import javafx.scene.control.Alert;
import org.example.javacomponent.AppVariables;

import java.io.FileReader;
import java.util.List;

public class StudentModifer {
    protected void showCriticalError(String msg) {
        /* reusable method to show error box */
        Alert alert = new Alert(Alert.AlertType.ERROR); // show an error box if files not found
        alert.setHeaderText("An error occurred trying to read CSV file as a Subprocess.");
        alert.setContentText(msg); // show error information
        alert.setResizable(true);
        alert.showAndWait();
        System.exit(0); // close after critical error
    }
    protected void modify(String inputValue, String fileName, List errorArrayList, int counter){
        String[] splitPairs = errorArrayList.get(counter).toString().split("="); // split key and value into an Array
        int rowIndex = Integer.parseInt(splitPairs[0]) - 1;
        String type = splitPairs[1];  // get the row AND position of the found error

        try{
            // read the entire csv file brand new (with errors)
            CSVReader reader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), fileName)));
            List<String[]> csvData = reader.readAll(); // create List interface



        } catch (Exception e) {
            showCriticalError(e.toString()); // handle error if critical error arises
        }
    }
}
