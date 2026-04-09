/* Controls the initialization of the scene */

package org.example.javacomponent;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.javacomponent.controllers.ErrorCorrectorController;
import org.example.javacomponent.controllers.ProgressSceneController;

import java.io.IOException;
import java.util.HashMap;

public class SceneInitializer extends Application {
    @Override
    public void start(Stage PrimaryStage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(SceneInitializer.class.getResource("startingStage.fxml"));

        Parent root = FXMLLoader.load(getClass().getResource("startingStage.fxml"));
        PrimaryStage.setTitle("Library Data Viewer");
        PrimaryStage.setScene(new Scene(root, 400, 300));
        PrimaryStage.setResizable(false); // prevent from resizing
        PrimaryStage.show();
    }

    public void progressScene(ActionEvent actionEvent) throws Exception{
        /* create new stage for the progress when analyzing csv files */
        FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("csvAnalyzingProgress.fxml"));
        Parent root = (Parent)sceneLoader.load(); // run initialize() method in controller

        // fetch the controller's class and assign directory String to class variable
        ProgressSceneController controller = sceneLoader.<ProgressSceneController>getController();

        Stage progressStage = new Stage();
        controller.setProgressStage(progressStage);
        controller.setVariables();

        progressStage.setScene(new Scene(root, 400, 100));
        progressStage.setTitle("Analyzing CSV Files");
        progressStage.setResizable(false);
        progressStage.show();

        controller.startCorrector(); // proceed to next scene

        // identify actionEvent source and close previous scene
        Stage prevStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        prevStage.close();
    }

    public void correctorScene(
            Stage progressStage,
            HashMap<Integer, Integer> bookErrorTracker,
            HashMap<Integer, String> studentErrorTracker,
            HashMap<Integer, String> transactionsErrorTracker
    ) throws Exception {
        /* create new stage for the progress when correcting csv format errors */
        FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("errorCorrector.fxml"));
        Parent root = (Parent)sceneLoader.load(); // run initialize() method in controller

        // fetch the controller's class and assign directory String to class variable
        ErrorCorrectorController controller = sceneLoader.getController();

        Stage correctorStage = new Stage();
        controller.setMaps(bookErrorTracker, studentErrorTracker, transactionsErrorTracker);
        controller.setCorrectorStage(correctorStage);
        controller.mainProcess(); // start main process

        correctorStage.setScene(new Scene(root, 700, 250));
        correctorStage.setTitle("Library Data Viewer");
        correctorStage.setResizable(false);
        correctorStage.show();

        // close the progress scene
        progressStage.close();
    }
}
