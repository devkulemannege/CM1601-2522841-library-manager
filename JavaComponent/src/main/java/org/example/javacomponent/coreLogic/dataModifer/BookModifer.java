package org.example.javacomponent.coreLogic.dataModifer;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javafx.scene.control.Alert;
import org.example.javacomponent.AppVariables;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class BookModifer {
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
        int elementIndex = Integer.parseInt(splitPairs[1]);  // get the row AND position of the found error

        try {
            // read the entire csv file brand new (with errors)
            CSVReader reader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), fileName)));
            List<String[]> csvData = reader.readAll(); // create List interface

            // find row with error and replace value with new value
            for (int i = 0; i < csvData.size(); i++) {
                if (i == rowIndex){
                    if (elementIndex == 1){ // replace ISBN
                        csvData.get(rowIndex)[elementIndex] = inputValue;
                        break; // stop method and return to prevent error from split attempt
                    }

                    String[] splitInputValue = inputValue.split(" ");

                    if (elementIndex == 3){
                        csvData.get(rowIndex)[elementIndex] = splitInputValue[0]; // replace no. of copies
                        break;
                    } else if (elementIndex == 4){
                        csvData.get(rowIndex)[elementIndex] = splitInputValue[1]; // replace no. of available
                        break;
                    }
                }
            }

            // over-write entire csv file with updated data
            CSVWriter writer = new CSVWriter(new FileWriter(String.format("%s\\%s", AppVariables.getDirectory(), fileName)),
                    CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER); // prevent quotes from writing
            writer.writeAll(csvData); // over-write

            reader.close(); writer.close(); // close reader, writer
        } catch (Exception e) {
            showCriticalError(e.toString()); // handle error if critical error arises
        }
    }
}
