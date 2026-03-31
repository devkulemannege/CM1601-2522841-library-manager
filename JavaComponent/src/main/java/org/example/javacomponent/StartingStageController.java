/* Controls the elements in the scene */

package org.example.javacomponent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class StartingStageController {
    private String directory;
    static boolean customDirGiven = false;
    static boolean bookExists = false, studentExists = false, transactionExists= false; // initialize file exist flags

    @FXML
    private Button startButton;
    @FXML
    private Button selectDirButton;
    @FXML
    private Label pathDisplay;
    @FXML
    private Label noFileError;

    public void selectDirButtonClick() {
        /* retrieve custom csv directoryObj and store in class variable */
        File tempDirHolder;

        DirectoryChooser dirChooser = new DirectoryChooser();
        this.directory = (dirChooser.showDialog(null)).getAbsolutePath(); // get the folder path in String

        pathDisplay.setText(directory);
        customDirGiven = true;
    }
    
    public void startButtonClick(){
        if (!(customDirGiven)) {this.directory = "C:\\LibraryData";}

        File accessDirectory = new File(directory);
        File[] files = accessDirectory.listFiles(); // see all files which exists inside the directory

        if (files != null){
            for (File file : files){
                System.out.println(file);
            }
        } else {
            noFileError.setText("CSV files not found!");
        }
    }
}
