package org.example.javacomponent.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class ErrorCorrectorController {
    private HashMap<Integer, Integer> bookErrorTracker;
    private HashMap<String, Integer> studentErrorTracker;
    private HashMap<String, Integer> transactionsErrorTracker;

    public void setMaps(HashMap<Integer, Integer> bookErrorTracker, HashMap<String, Integer> studentErrorTracker, HashMap<String, Integer> transactionsErrorTracker){
        /* Set error tracking maps of the class */
        this.bookErrorTracker = bookErrorTracker;
        this.studentErrorTracker = studentErrorTracker;
        this.transactionsErrorTracker = transactionsErrorTracker;
    }

    @FXML
    private AnchorPane anchorPane;

    @FXML
    protected void initialize(){
        // todo: show errors one by one and iterate to the next when ok is clicked. show total errors on top
        for (int i=0; i<5; i++){
            Label label = new Label(); // NEW label every time
            label.setText("Hello " + (i + 1));

            // optional: position it in AnchorPane
            AnchorPane.setTopAnchor(label, 10.0 + i * 25);
            AnchorPane.setLeftAnchor(label, 10.0);

            anchorPane.getChildren().add(label);
        }
    }
}
