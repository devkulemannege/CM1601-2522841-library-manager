package org.example.javacomponent.coreLogic.dataModifer;

import com.opencsv.CSVReader;
import javafx.scene.control.Alert;
import org.example.javacomponent.AppVariables;
import org.example.javacomponent.coreLogic.Alerts;

import java.io.FileReader;
import java.util.List;

public class StudentModifer {
    protected void modify(String inputValue, String fileName, List errorArrayList, int counter){
        String[] splitPairs = errorArrayList.get(counter).toString().split("="); // split key and value into an Array
        int rowIndex = Integer.parseInt(splitPairs[0]) - 1;
        String type = splitPairs[1];  // get the row AND position of the found error

        try{
            // read the entire csv file brand new (with errors)
            CSVReader reader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), fileName)));
            List<String[]> csvData = reader.readAll(); // create List interface



        } catch (Exception e) {
            Alerts.showCriticalError(e.toString()); // handle error if critical error arises
        }
    }
}
