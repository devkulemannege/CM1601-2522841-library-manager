package org.example.javacomponent.controllers;

import com.opencsv.CSVReader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.javacomponent.AppVariables;
import org.example.javacomponent.SceneInitializer;
import org.example.javacomponent.corelogic.Alerts;
import org.example.javacomponent.corelogic.datamodifer.DataModifer;
import org.example.javacomponent.corelogic.inputvalidation.InputValidator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ErrorCorrectorController {
    private Stage correctorStage; // placeholder for the stage

    /* iteration counters */
    private int bookCounter = 0, studentCouter = 0, transactionsCounter = 0;

    /* HashMaps converted to ArrayList */
    private ArrayList<Map.Entry<Integer, Integer>> bookErrorArrayLst;
    private ArrayList<Map.Entry<Integer, String>> studentErrorArrayLst;
    private ArrayList<Map.Entry<Integer, String>> transactionsErrorArrayLst;

    public void setMaps(
            HashMap<Integer, Integer> bookErrorTracker,
            HashMap<Integer, String> studentErrorTracker,
            HashMap<Integer, String> transactionsErrorTracker
    ){
        /* get HashMaps as converted Array Lists */
        bookErrorArrayLst = new ArrayList<>(bookErrorTracker.entrySet());
        studentErrorArrayLst = new ArrayList<>(studentErrorTracker.entrySet());
        transactionsErrorArrayLst = new ArrayList<>(transactionsErrorTracker.entrySet());
    }

    public void setCorrectorStage(Stage correctorStage) {
        /* get the stage representing the corrector scene */
        this.correctorStage = correctorStage;
    }
    @FXML
    private Label rowLabel;
    @FXML
    private Label rowValueLabel;
    @FXML
    private Label fileName;
    @FXML
    private TextField correctValueInput;
    @FXML
    private Label errorCounter;
    @FXML
    private Label inputFormat;

    @FXML
    protected void initialize(){
        /* initialize but does not do anything. Used to get the root*/
    }

    private String[] getRowFromCsv(String fileName, int key){
        /* find and return the row from book.csv */
        int loopCounter = 0; // counter for the for loop
        try {
            CSVReader reader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), fileName)));
            List<String[]> csvData = reader.readAll(); // create List interface
            csvData.removeFirst(); // remove column headers

            for (String[] row : csvData) {
                if (loopCounter+2 == key){ // add 2 since first row is removed and counter starts from 0
                    reader.close(); // close CSVReader

                    return row;
                }
                loopCounter++;
            }
        } catch (FileNotFoundException e){
            Alerts.showCriticalError("Missing CSV Files"); // handle if file doesn't exist
        } catch (Exception e) {
            Alerts.showCriticalError(e.toString()); // handle error if critical error arises
        }
        return(new String[0]); // return empty array
    }

    @FXML
    protected void proceedIteration() {
        /* handles input values and ALL input validations */
        boolean result = false; // initialize
        int index = 0;

        String fileNameValue = fileName.getText();
        String inputValue = correctValueInput.getCharacters().toString();

        // validate the input given by the user
        result = new InputValidator().insertData(inputValue, fileNameValue,
                bookErrorArrayLst, studentErrorArrayLst, transactionsErrorArrayLst,
                bookCounter-1, studentCouter-1, transactionsCounter-1);

        if (result) {
            new DataModifer().insertData(
                    inputValue, fileNameValue,
                    bookErrorArrayLst, studentErrorArrayLst, transactionsErrorArrayLst,
                    bookCounter-1, studentCouter-1, transactionsCounter-1
            );
            iterateThroughErrors(); // continue to next error
        } else {
            Alerts.incorrectTextInput();
        }
    }

    public void iterateThroughErrors(){
        /* Iterate through all errors and let the user resolve them */
        inputFormat.setText(""); // revert to default empty string

        int totalErrorCount = bookErrorArrayLst.size() + studentErrorArrayLst.size() + transactionsErrorArrayLst.size();
        int resolvedErrorCount = bookCounter + studentCouter + transactionsCounter;

        // display resolved errors against num of errors remaining
        errorCounter.setText(String.format("%s/%s", resolvedErrorCount, totalErrorCount));

        // if all errors resolved, display options menu
        if (resolvedErrorCount == totalErrorCount){
            try {
                new SceneInitializer().optionsScene(correctorStage);
            } catch (Exception e) {
                Alerts.showCriticalJavafxError(e.toString());
            }
        }

        // BOOK.CSV ================= check if no errors or all errors resolved
        if (!(bookErrorArrayLst.isEmpty() || bookCounter == bookErrorArrayLst.size())){
            bookCounter++;

            // split key and value into an Array
            String[] splitPairs = bookErrorArrayLst.get(bookCounter-1).toString().split("=");
            int key = Integer.parseInt(splitPairs[0]); // convert to int
            int value = Integer.parseInt(splitPairs[1]);

            fileName.setText("book.csv");

            String[] row = getRowFromCsv("book.csv", key); // find row using method
            String rowString = Arrays.toString(row);
            rowLabel.setText(rowString.substring(1, rowString.length()-1)); // remove [ & ] from array formatting

            // if error is in copies/availability or isbn
            if (value == 3 || value == 4){
                inputFormat.setText("Enter copies and available (space-separated) eg: 2 1");
                rowValueLabel.setText(String.format("Copies: %s, Available Copies: %s", row[3], row[4]));
            } else {
                inputFormat.setText("Adhere to ISBN-13 format");
                rowValueLabel.setText(row[value]); // show value with error within row
            }
        }
        // STUDENT.CSV ================= check if no errors or all errors resolved
        else if (!(studentErrorArrayLst.isEmpty() || studentCouter == studentErrorArrayLst.size())){
            studentCouter++;

            // split key and value into an Array
            String[] splitPairs = studentErrorArrayLst.get(studentCouter-1).toString().split("=");
            int key = Integer.parseInt(splitPairs[0]); // convert to int
            String type = splitPairs[1];

            fileName.setText("student.csv");

            String[] row = getRowFromCsv("student.csv", key); // find row using method
            String rowString = Arrays.toString(row);
            rowLabel.setText(rowString.substring(1, rowString.length()-1)); // remove [ & ] from array formatting

            inputFormat.setText("ID must be unique, consisting of eight digits.");

            if (type.equals("invalid")){
                rowValueLabel.setText(String.format("Invalid -> %s", row[0]));
            } else {
                rowValueLabel.setText(String.format("Duplicate -> %s", row[0]));
            }
        }
        // TRANSACTIONS.CSV ================= check if no errors or all errors resolved
        else if (!(transactionsErrorArrayLst.isEmpty() || transactionsCounter == transactionsErrorArrayLst.size())){
            transactionsCounter++;

            // split key and value into an Array
            String[] splitPairs = transactionsErrorArrayLst.get(transactionsCounter-1).toString().split("=");
            int key = Integer.parseInt(splitPairs[0]); // convert to int
            String type = splitPairs[1];

            fileName.setText("transactions.csv");

            String[] row = getRowFromCsv("transactions.csv", key); // find row using method
            String rowString = Arrays.toString(row);
            rowLabel.setText(rowString.substring(1, rowString.length()-1)); // remove [ & ] from array formatting

            inputFormat.setText("Must follow DD/MM/YYYY format with forward-slashes.");

            if (type.equals("day")){
                rowValueLabel.setText(String.format("\"date\" is incorrect -> %s", row[0]));
            } else if (type.equals("month")){
                rowValueLabel.setText(String.format("\"month\" is incorrect -> %s", row[0]));
            } else if (type.equals("year")){
                rowValueLabel.setText(String.format("\"year\" is incorrect -> %s", row[0]));
            } else if (type.equals("invalid")){
                rowValueLabel.setText(String.format("Format is incorrect -> %s", row[0]));
            }
        }
    }
}
