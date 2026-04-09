package org.example.javacomponent.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private Button doneButton;
    @FXML
    private Label rowLabel;
    @FXML
    private Label rowValueLabel;
    @FXML
    private Label fileName;
    @FXML
    private TextField correctValueInput;

    @FXML
    protected void initialize(){
        /* initialize but does not do anything. Used to get the root*/
        System.out.println("ErrorCorrectorController initialize");
    }

    private String getRow(){
        System.out.println("");
        return "";
    }

    public void mainProcess(){
        int count = 0; // increments with each iteration
        for (HashMap.Entry<Integer, Integer> i : bookErrorTracker.entrySet()){

            fileName.setText("book.csv");
            rowLabel.setText(i.toString());
        }
    }
}
