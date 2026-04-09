package org.example.javacomponent.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import org.example.javacomponent.AppVariables;
import org.example.javacomponent.SceneInitializer;
import org.example.javacomponent.coreLogic.DataExtractor;

import java.util.HashMap;

public class ProgressSceneController {
    private Stage progressStage;
    private HashMap<Integer, Integer> bookErrorTracker;
    private HashMap<Integer, String> studentErrorTracker;
    private HashMap<Integer, String> transactionsErrorTracker;

    public void setProgressStage(Stage progressStage) {
        this.progressStage = progressStage;
    }

    @FXML
    private Label statusUpdate;
    @FXML
    private ProgressBar progressBar;

    @FXML
    protected void initialize() {
        /* auto runs when scene starts */
        progressBar.setProgress(progressBar.INDETERMINATE_PROGRESS); // change to intermediate type progress bar
    }

    public void setVariables() throws Exception {
        /* acts as a setter and a "jump-started" for the main csv analysis process */
        statusUpdate.setText(String.format("Analyzing: %s", AppVariables.getDirectory()));

        DataExtractor extractor = new DataExtractor();
        extractor.mainProcess(); // start extraction process

        // get the error tracker maps for each csv file. These variables are to be sent to corrector scene
        this.bookErrorTracker = extractor.getBookErrorTracker();
        this.studentErrorTracker = extractor.getStudentErrorTracker();
        this.transactionsErrorTracker = extractor.getTransactionsErrorTracker();
    }

    public void startCorrector() throws Exception {
        // start scene for format correction
        if (bookErrorTracker.isEmpty() && studentErrorTracker.isEmpty() && transactionsErrorTracker.isEmpty()) {
            System.out.println("No book, student and transactions errors were found");
            // todo: create logic to skip correction
        } else {
            SceneInitializer startScene = new SceneInitializer();
            startScene.correctorScene(progressStage, bookErrorTracker, studentErrorTracker, transactionsErrorTracker);
        }
    }
}


