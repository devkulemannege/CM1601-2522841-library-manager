package org.example.javacomponent.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class ErrorCorrectorController {
    private Stage correctorStage;
    private HashMap<Integer, Integer> bookErrorTracker;
    private HashMap<Integer, String> studentErrorTracker;
    private HashMap<Integer, String> transactionsErrorTracker;

    public void setMaps(HashMap<Integer, Integer> bookErrorTracker, HashMap<Integer, String> studentErrorTracker, HashMap<Integer, String> transactionsErrorTracker){
        /* Set error tracking maps of the class */
        this.bookErrorTracker = bookErrorTracker;
        this.studentErrorTracker = studentErrorTracker;
        this.transactionsErrorTracker = transactionsErrorTracker;
    }

    public void setCorrectorStage(Stage correctorStage) {
        /* get the stage representing the corrector scene */
        this.correctorStage = correctorStage;
    }

    @FXML
    private AnchorPane anchorPane;

    @FXML
    protected void initialize(){
        // todo: show errors one by one and iterate to the next when ok is clicked. show total errors on top for (int i=0; i<5; i++){
        for (Integer key : transactionsErrorTracker.keySet()) {
            System.out.println(key);
        }
    }
}
