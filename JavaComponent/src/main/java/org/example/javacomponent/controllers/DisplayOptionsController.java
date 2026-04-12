package org.example.javacomponent.controllers;

import com.opencsv.CSVReader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.javacomponent.AppVariables;
import org.example.javacomponent.corelogic.Alerts;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DisplayOptionsController {
    private Stage displayOptionsStage;

    @FXML
    public Button bookOption;
    @FXML
    public Label bookRow;
    @FXML
    public Label bookCol;
    @FXML
    public Button studentOption;
    @FXML
    public Label studentRow;
    @FXML
    public Label studentCol;
    @FXML
    public Button transactionsOption;
    @FXML
    public Label transactionRow;
    @FXML
    public Label transactionCol;

    public void setDisplayOptionsStage(Stage displayOptionsStage) {
        this.displayOptionsStage = displayOptionsStage;
    }

    protected String[] getRowCol(String fileName){
        /* return the no. of rows and columns for given csv file */
        String[] valueHolder = new String[2];

        try {
            CSVReader reader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), fileName)));
            List<String[]> csvData = reader.readAll(); // create List interface
            csvData.removeFirst(); // remove column headers

            valueHolder[0] = Integer.toString(csvData.size()); // get no. of rows
            valueHolder[1] = Integer.toString(csvData.getFirst().length); // get no. of columns
        } catch (IOException e) {
            Alerts.showCriticalError(e.toString());
        }
        return valueHolder;
    }

    @FXML
    protected void initialize() {
        /* initialize and get the root for method in SceneInitializer */
        String bookFileName = "book.csv";
        String studentFileName = "student.csv";
        String transactionsFileName = "transactions.csv";

        // get no. of rows and cols for each file
        String[] retreivedBookParams = getRowCol(bookFileName);
        String[] retreivedStudentParams = getRowCol(studentFileName);
        String[] retreivedTransactionParams = getRowCol(transactionsFileName);

        bookRow.setText(retreivedBookParams[0]);
        bookCol.setText(retreivedBookParams[1]);

        studentRow.setText(retreivedStudentParams[0]);
        studentCol.setText(retreivedStudentParams[1]);

        transactionRow.setText(retreivedTransactionParams[0]);
        transactionCol.setText(retreivedTransactionParams[1]);
    }
}
