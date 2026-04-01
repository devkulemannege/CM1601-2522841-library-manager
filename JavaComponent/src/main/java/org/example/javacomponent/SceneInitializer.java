/* Controls the intialization of the scene */

package org.example.javacomponent;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void progressScene(ActionEvent actionEvent, String directory) throws Exception{
        /* create new stage for the progress when analyzing csv files */
        FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("csvAnalyzingProgress.fxml"));
        Parent root = (Parent)sceneLoader.load();

        // fetch the controller's class and assign directory String to class variable
        ProgressSceneController controller = sceneLoader.<ProgressSceneController>getController();
        controller.setDirectory(directory); // assign using setter

        Stage newStage = new Stage();
        newStage.setScene(new Scene(root, 400, 100));
        newStage.setTitle("Analyzing CSV Files");
        newStage.setResizable(false);
        newStage.show();

        // identify actionEvent source and close previous scene
        Stage prevStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        prevStage.close();
    }
}
