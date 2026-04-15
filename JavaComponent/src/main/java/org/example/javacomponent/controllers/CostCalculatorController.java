package org.example.javacomponent.controllers;

import com.opencsv.CSVReader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.javacomponent.AppVariables;
import org.example.javacomponent.SceneInitializer;
import org.example.javacomponent.corelogic.Alerts;

import java.io.FileReader;
import java.util.List;

public class CostCalculatorController {
    private Stage costCalculatorStage;
    private int totalNoOfBooks;
    private double totalCost;

    @FXML
    private Label totalBooks;
    @FXML
    private Label booksCost;
    @FXML
    private Label averageCost;

    public void setCostCalculatorStage(Stage costCalculatorStage) {
        this.costCalculatorStage = costCalculatorStage;
    }

    @FXML
    protected void initialize() {
        /* initialize and auto run code below. Used to get the root*/
        averageCost.setStyle("-fx-font-weight: bold");

        try{
            CSVReader reader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), "book.csv")));
            List<String[]> csvData = reader.readAll(); // create List interface
            csvData.removeFirst(); // remove column headers

            totalNoOfBooks = csvData.size(); // get total no. of books

            for (String[] row : csvData) {
                totalCost += Double.parseDouble(row[5]); // get total cost of books
            }

            totalBooks.setText(String.valueOf(totalNoOfBooks));
            booksCost.setText(String.format("$%.2f", totalCost));
        } catch (Exception e) {
            Alerts.showCriticalError(e.toString());
        }
    }

    @FXML
    protected void calculateBtnClick() {
        /* show average calculation result */
        double avergeCostValue = totalNoOfBooks * totalCost;
        averageCost.setText(String.format("$%.2f", avergeCostValue)); // display calculated average cost
    }

    @FXML
    protected void previousBtnClick() {
        /* Go back to options menu when required */
        new SceneInitializer().optionsScene(costCalculatorStage);
    }
}
