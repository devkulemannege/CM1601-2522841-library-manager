package org.example.javacomponent.controllers;

import com.opencsv.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.example.javacomponent.AppVariables;
import org.example.javacomponent.SceneInitializer;
import org.example.javacomponent.controllers.tablemodels.IssueReportModel;
import org.example.javacomponent.corelogic.Alerts;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class BookIssueReportController {
    private Stage bookIssueReportStage;

    public void setBookIssueReportStage(Stage bookIssueReportStage) {
        this.bookIssueReportStage = bookIssueReportStage;
    }

    @FXML
    private TextField dayInput;
    @FXML
    private TextField monthInput;
    @FXML
    private TextField yearInput;
    @FXML
    private Label resultLabel;
    @FXML
    private TableView<IssueReportModel> table;

    @FXML
    protected void initialize() {
        /* initialize and create table column objects. Used to get the root*/
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("studentId"));
    }

    @FXML
    protected void generateReport() {
        String day = "", month = "" , year = "";

        try {
            day = dayInput.getText();
            month = monthInput.getText();
            year = yearInput.getText();

            // validate user input and show error if required
            if (!((0 < Integer.parseInt(day) && Integer.parseInt(day) < 32) &&
                    (0 < Integer.parseInt(month) && Integer.parseInt(month) < 13) &&
                    (1999 < Integer.parseInt(year) && year.length()<5))){
                Alerts.incorrectTextInput();
            }
        } catch (NumberFormatException e) {
            Alerts.incorrectTextInput();
        }

        try {
            CSVReader reader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), "transactions.csv")));
            List<String[]> csvData = reader.readAll(); // create List interface
            csvData.removeFirst(); // remove column headers

            ObservableList<IssueReportModel> records = FXCollections.observableArrayList();

            for (String[] row  : csvData) {
                String[] splitDate = row[0].split("/");

                // only iterate if the given date values match
                if (splitDate[0].equals(day) && splitDate[1].equals(month) && splitDate[2].equals(year)){;
                    if (row[3].equals("1")){
                        // add row to an observable List if date match and transaction type is 1
                        records.add(new IssueReportModel(row[1], row[2]));
                    }
                }
            }
            table.setItems(records); // set items of record to the table

            if (records.isEmpty()){
                resultLabel.setStyle("-fx-font-weight: bold");
                resultLabel.setText("No Transactions Found.");
            } else {
                resultLabel.setStyle("-fx-font-weight: bold");
                resultLabel.setText(String.format("Total Transactions: %s", records.size()));
            }

        } catch (FileNotFoundException e){
            Alerts.showCriticalError("Missing CSV Files"); // handle if file doesn't exist
        } catch (Exception e) {
            System.out.println("1");
            Alerts.showCriticalError(e.toString()); // handle error if critical error arises
        }
    }

    @FXML
    protected void backBtnOnAction() {
        /* Go back to options menu when required */
        new SceneInitializer().optionsScene(bookIssueReportStage);
    }

}
