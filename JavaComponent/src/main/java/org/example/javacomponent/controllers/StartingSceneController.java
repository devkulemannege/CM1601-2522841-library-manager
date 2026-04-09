package org.example.javacomponent.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import org.example.javacomponent.AppVariables;
import org.example.javacomponent.SceneInitializer;

import java.io.File;

public class StartingSceneController {
    boolean customDirGiven = false;
    boolean bookExists = false, studentExists = false, transactionExists= false; // initialize file exist flags

    @FXML
    private Button startButton;
    @FXML
    private Button selectDirButton;
    @FXML
    private Label pathDisplay;
    @FXML
    private Label noFileError;

    void setDirectory(String directory) {
        /* class-exclusive method. Does not follow encapsulation */
        AppVariables.setDirectory(directory);
        pathDisplay.setText(AppVariables.getDirectory()); // update selected directory ui
    }

    void showNoFileError() {
        /* reusable method to show error box */
        Alert alert = new Alert(Alert.AlertType.ERROR); // show an error box if files not found
        alert.setContentText("CSV files not found in directory.");
        alert.setHeaderText(null);
        alert.show();
    }

    @FXML
    protected void selectDirButtonClick(ActionEvent actionEvent) throws Exception {
        /* retrieve custom csv directoryObj and store in class variable */
        File tempDirHolder;

        DirectoryChooser dirChooser = new DirectoryChooser();
        AppVariables.setDirectory(dirChooser.showDialog(null).getAbsolutePath()); // get the folder path in String

        pathDisplay.setText(AppVariables.getDirectory());
        customDirGiven = true;
    }

    @FXML
    protected void startButtonClick(ActionEvent actionEvent) throws Exception{
        /* check is all csv files exists before proceeding. Or else show error */
        if (!(customDirGiven)) {setDirectory("C:\\LibraryData");}

        File accessDirectory = new File(AppVariables.getDirectory());
        File[] files = accessDirectory.listFiles(); // see all files which exists inside the directory

        // confirm csv file's existence by changing flag
        if (files != null){
            for (File file : files){
                if (file.toString().equals(String.format("%s\\book.csv", AppVariables.getDirectory()))) {bookExists = true;}
                if (file.toString().equals(String.format("%s\\student.csv", AppVariables.getDirectory()))) {studentExists = true;}
                if (file.toString().equals(String.format("%s\\transactions.csv", AppVariables.getDirectory()))) {transactionExists = true;}
            }
        } else {
            showNoFileError();
        }

        if (bookExists && studentExists && transactionExists){
            SceneInitializer startScene = new SceneInitializer();
            startScene.progressScene(actionEvent); // start next scene and send directory info
        } else {
            showNoFileError();
        }
    }
}
