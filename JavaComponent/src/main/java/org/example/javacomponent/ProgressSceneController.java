package org.example.javacomponent;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.control.ProgressBar;
import org.example.javacomponent.coreLogic.DataExtractor;

public class ProgressSceneController {
    private String directory;

    @FXML
    private Label statusUpdate;
    @FXML
    private ProgressBar progressBar;

    @FXML
    protected void initialize() {
        /* auto runs when scene starts */
        System.out.println("Progress window started");
        progressBar.setProgress(progressBar.INDETERMINATE_PROGRESS); // change to intermediate type progress bar
    }

    public void setDirectory(String directory) throws Exception {
        /* acts as a setter and a "jump-started" for the main csv analysis process */
        this.directory = directory;
        statusUpdate.setText("Analyzing: " + this.directory);

        DataExtractor extractor = new DataExtractor(directory);
        extractor.mainProcess(); // start extraction process
    }
}
