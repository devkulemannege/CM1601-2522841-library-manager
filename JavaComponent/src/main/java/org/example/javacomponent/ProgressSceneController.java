package org.example.javacomponent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProgressSceneController {
    @FXML
    private Label statusUpdate;

    @FXML
    protected void initialize(){
        /* auto runs when scene starts */
        statusUpdate.setText("The text here has been updated");
    }
}
