package org.example.javacomponent.coreLogic;

import javafx.scene.control.Alert;

public class Alerts {
    public static void showCriticalError(String msg) {
        /* reusable method to show error box */
        Alert alert = new Alert(Alert.AlertType.ERROR); // show an error box if files not found
        alert.setHeaderText("An error occurred trying to read CSV file as a Subprocess.");
        alert.setContentText(msg); // show error information
        alert.setResizable(true);
        alert.showAndWait();
        System.exit(0); // close after critical error
    }

    public static void incorrectTextInput(){
        /* reusable method to show error box */
        Alert alert = new Alert(Alert.AlertType.WARNING); // show an error box if files not found
        alert.setHeaderText(null);
        alert.setContentText("Please enter the corrected value in appropriate format!");
        alert.showAndWait();
    }

    public static void duplicateStudentInput(){
        /* reusable method to show error box */
        Alert alert = new Alert(Alert.AlertType.WARNING); // show an error box if files not found
        alert.setHeaderText(null);
        alert.setContentText("Given Student ID already exists!");
        alert.showAndWait();
    }

}
