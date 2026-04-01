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
    protected void initialize(){
        /* auto runs when scene starts */
        System.out.println("Progress window started");
    }

    public void setDirectory(String directory) {
        this.directory = directory;
        statusUpdate.setText("Analyzing: " + this.directory);
        mainProcess(); // start main process
    }

    void mainProcess(){
        
    }
}
