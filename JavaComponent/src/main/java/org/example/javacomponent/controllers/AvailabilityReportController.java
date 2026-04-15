package org.example.javacomponent.controllers;

import com.opencsv.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.javacomponent.AppVariables;
import org.example.javacomponent.SceneInitializer;
import org.example.javacomponent.controllers.tablemodels.AvailableReportModel;
import org.example.javacomponent.corelogic.Alerts;

import java.io.*;
import java.util.Date;
import java.util.List;

public class AvailabilityReportController {
    private Stage availabilityReportStage;

    @FXML
    private TableView<AvailableReportModel> availabilityTable;
    @FXML
    private Button searchBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button exportBtn;
    @FXML
    private TextField searchBar;

    // collection to hold table data
    ObservableList<AvailableReportModel> records = FXCollections.observableArrayList();

    public void setAvailabilityReportStage(Stage availabilityReportStage) {
        this.availabilityReportStage = availabilityReportStage;
    }

    @FXML
    protected void initialize() {
        /* initialize and create table column objects. Used to get the root*/
        availabilityTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        availabilityTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        availabilityTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("copiesAvailable"));
        availabilityTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("currentBurrower"));
    }

    @FXML
    protected void generateReport(){
        String searchInput = searchBar.getText().toLowerCase();
        int issueCount = 0, copiesAvailable = 0;
        String currentBurrower = "";

        availabilityTable.getItems().clear();

        try{
            // read csv files for all 3 files
            CSVReader bookReader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), "book.csv")));
            List<String[]> bookCsvData = bookReader.readAll(); // create List interface
            bookCsvData.removeFirst(); // remove column headers

            CSVReader studentReader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), "student.csv")));
            List<String[]> studentCsvData = studentReader.readAll(); // create List interface
            studentCsvData.removeFirst(); // remove column headers
            
            CSVReader transactionReader = new CSVReader(new FileReader(String.format("%s\\%s", AppVariables.getDirectory(), "transactions.csv")));
            List<String[]> transactionCsvData = transactionReader.readAll(); // create List interface
            transactionCsvData.removeFirst(); // remove column headers

            for (String[] row : bookCsvData){
                if (row[2].toLowerCase().equals(searchInput)){ // if search keyword is equal to title in row

                    for (String[] transRow : transactionCsvData){
                        if (transRow[1].equals(row[0]) && transRow[3].equals("1")){ // if book id is equal to id in transaction data row
                            issueCount++; // increment if a book copy has been issued
                        } else if (transRow[1].equals(row[0]) && transRow[3].equals("2")){
                            issueCount--; // decrement if a book copy has been returned
                        }

                        for (String[] studentRow: studentCsvData){
                            if (studentRow[0].equals(transRow[2]) && transRow[1].equals(row[0])){
                                currentBurrower = studentRow[1]; // get the student's name using ID in transaction row
                            }
                        }
                    }
                    copiesAvailable = Integer.parseInt(row[4]) - issueCount; // calculate the no. of copies available

                    records.add(new AvailableReportModel( // record data to the table collection
                            row[0],
                            row[2],
                            String.valueOf(copiesAvailable),
                            currentBurrower
                    ));
                } else if (searchInput.toLowerCase().contains("*")){
                    // check if book title is greater or equal to the length of the required title
                    if (row[2].length() >= searchInput.substring(0, searchInput.length()-1).length()){
                        // check if the wild card characters are included within the iterating book title
                        if (row[2].toLowerCase().contains(searchInput.substring(0, searchInput.length()-1).toLowerCase())){
                            issueCount = 0; // reset the counter every iteration
                            currentBurrower = "--"; // reset the value in variable every iteration

                            for (String[] transRow : transactionCsvData) { // track how many copies are issued
                                if (transRow[1].equals(row[0]) && transRow[3].equals("1")) {
                                    issueCount++;
                                } else if (transRow[1].equals(row[0]) && transRow[3].equals("2")) {
                                    issueCount--;
                                }

                                for (String[] studentRow : studentCsvData) {
                                    if (studentRow[0].equals(transRow[2]) && transRow[1].equals(row[0])) {
                                        currentBurrower = studentRow[1];
                                    }
                                }
                            }

                            copiesAvailable = Integer.parseInt(row[4]) - issueCount; // calculate the no. of copies available

                            records.add(new AvailableReportModel( // record data to the table collection
                                    row[0],
                                    row[2],
                                    String.valueOf(copiesAvailable),
                                    currentBurrower
                            ));
                        }
                    }
                }
            }

            availabilityTable.setItems(records); // add collection to the table

        } catch (Exception e){
            Alerts.showCriticalError(e.toString()); // handle error if critical error arises
        }
    }

    @FXML
    protected void exportBtnClicked(){
        Date date = new Date(); // get date information using date obj

        File file = new File(String.format("%s\\%s", AppVariables.getDirectory(), "AvailabilityReport.txt"));
        try {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file)); // initializer bufferedwriter obj

            writer.write(String.format("Book Availability Report as of, %s\n\n", date));
            writer.write("Book ID | Book Title | Copies Available | Current Burrower\n");

            for (AvailableReportModel record : records) {
                writer.write(String.format( // write availability report to text file
                        "%s %s %s %s\n",
                        record.getBookId(),
                        record.getBookTitle(),
                        record.getCopiesAvailable(),
                        record.getCurrentBurrower()
                ));
            }

            writer.close();

            Alerts.exportSuccess(); // show export successful confirmation
        } catch (IOException e) {
            Alerts.exportError(); // handle error if critical error arises
        } catch (Exception e){
            Alerts.showCriticalError(e.toString());
        }
    }

    @FXML
    protected void backBtnClicked(){
        /* Go back to options menu when required */
        new SceneInitializer().optionsScene(availabilityReportStage);
    }
}
