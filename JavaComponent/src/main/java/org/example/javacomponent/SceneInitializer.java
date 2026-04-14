/* Controls the initialization of the scene */

package org.example.javacomponent;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import org.example.javacomponent.controllers.*;
import org.example.javacomponent.corelogic.Alerts;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class SceneInitializer extends Application {
    @Override
    public void start(Stage PrimaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("startingStage.fxml"));
        PrimaryStage.setTitle("Library Data Viewer");
        PrimaryStage.setScene(new Scene(root, 400, 300));
        PrimaryStage.setResizable(false); // prevent from resizing
        PrimaryStage.show();
    }

    public void progressScene(ActionEvent actionEvent) {
        /* create new stage for the progress when analyzing csv files */
        try {
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
        } catch (Exception e) {
            Alerts.showCriticalJavafxError(e.toString());
        }
    }

    public void correctorScene(
            Stage progressStage,
            HashMap<Integer, Integer> bookErrorTracker,
            HashMap<Integer, String> studentErrorTracker,
            HashMap<Integer, String> transactionsErrorTracker
    ) {
        /* create new stage for the progress when correcting csv format errors */
        try {
            FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("errorCorrector.fxml"));
            Parent root = (Parent)sceneLoader.load(); // run initialize() method in controller

            // fetch the controller's class
            ErrorCorrectorController controller = sceneLoader.getController();

            Stage correctorStage = new Stage();
            controller.setMaps(bookErrorTracker, studentErrorTracker, transactionsErrorTracker);
            controller.setCorrectorStage(correctorStage);
            controller.iterateThroughErrors(); // start main process

            correctorStage.setScene(new Scene(root, 700, 250));
            correctorStage.setTitle("Library Data Viewer");
            correctorStage.setResizable(false);
            correctorStage.show();

            // close the progress scene
            progressStage.close();
        } catch (Exception e) {
            Alerts.showCriticalJavafxError(e.toString());
        }
    }

    public void optionsScene(Stage previousStage) {
        /* create new stage for the optionsScene when correcting csv format errors */
        try {
            FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("displayOptions.fxml"));
            Parent root = (Parent)sceneLoader.load(); // run initialize() method in controller

            // fetch the controller's class and assign directory String to class variable
            DisplayOptionsController controller = sceneLoader.getController();

            Stage optionsStage = new Stage();
            controller.setDisplayOptionsStage(optionsStage);

            optionsStage.setScene(new Scene(root, 500, 400));
            optionsStage.setTitle("Library Data Viewer");
            optionsStage.setResizable(false);
            optionsStage.show();

            // close the progress scene
            previousStage.close();
        } catch (Exception e) {
            Alerts.showCriticalJavafxError(e.toString());
        }
    }

    public void bookIssueReportScene(Stage optionsStage) {
        /* create new stage for the bookIssueReportScene when correcting csv format errors */
        try {
            FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("bookIssueReport.fxml"));
            Parent root = (Parent)sceneLoader.load(); // run initialize() method in controller

            ScrollPane sp = new ScrollPane();
            sp.setContent(root);

            // fetch the controller's class and assign directory String to class variable
            BookIssueReportController controller = sceneLoader.getController();

            Stage bookIssueReportStage = new Stage();
            controller.setOptionsStage(bookIssueReportStage);

            bookIssueReportStage.setScene(new Scene(sp, 800, 600));
            bookIssueReportStage.setTitle("Library Data Viewer");
            bookIssueReportStage.setResizable(true);
            bookIssueReportStage.show();

            // close the progress scene
            optionsStage.close();
        } catch (Exception e) {;
            Alerts.showCriticalJavafxError(e.toString());
        }
    }

    public void costCalculationScene(Stage optionsStage){
        /* create new stage for the costCalculationScene when correcting csv format errors */
        try {
            FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("costCalculator.fxml"));
            Parent root = (Parent)sceneLoader.load(); // run initialize() method in controller

            // fetch the controller's class and assign directory String to class variable
            CostCalculatorController controller = sceneLoader.getController();

            Stage costCalculatorStage = new Stage();
            controller.setCostCalculatorStage(costCalculatorStage);

            costCalculatorStage.setScene(new Scene(root, 400, 300));
            costCalculatorStage.setTitle("Library Data Viewer");
            costCalculatorStage.setResizable(true);
            costCalculatorStage.show();

            // close the progress scene
            optionsStage.close();
        } catch (Exception e) {;
            Alerts.showCriticalJavafxError(e.toString());
        }
    }

    public void availablityReportScene(Stage optionsStage){
        /* create new stage for the availablityReportScene when correcting csv format errors */
        try {
            FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("availabilityReport.fxml"));
            Parent root = (Parent)sceneLoader.load(); // run initialize() method in controller

            ScrollPane sp = new ScrollPane();
            sp.setContent(root);

            // fetch the controller's class and assign directory String to class variable
            AvailabilityReportController controller = sceneLoader.getController();

            Stage availablityReportStage = new Stage();
            controller.setAvailabilityReportStage(availablityReportStage);

            availablityReportStage.setScene(new Scene(sp, 800, 600));
            availablityReportStage.setTitle("Library Data Viewer");
            availablityReportStage.setResizable(true);
            availablityReportStage.show();

            // close the progress scene
            optionsStage.close();
        } catch (Exception e) {;
            System.out.println(Arrays.toString(e.getStackTrace()));
            Alerts.showCriticalJavafxError(e.toString());
        }
    }
}
