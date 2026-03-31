/* Controls the intialization of the scene */

package org.example.javacomponent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
}
