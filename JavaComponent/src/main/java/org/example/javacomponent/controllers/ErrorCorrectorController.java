package org.example.javacomponent.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorCorrectorController {
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
