package org.example.javacomponent.coreLogic.dataModifer;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.example.javacomponent.AppVariables;
import org.example.javacomponent.coreLogic.Alerts;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class BookModifier {
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
                        break; // stop loop and return to prevent error from split attempt
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
            Alerts.showCriticalError(e.toString()); // handle error if critical error arises
        }
    }
}
