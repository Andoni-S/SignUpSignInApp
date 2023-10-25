/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controller.LogInController;
import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class Application {    
    /**
     * This method is called when the JavaFX application is launched. It is used
     * to initialize the primary stage (the main window) and set up the user
     * interface of the application.
     *
     * @param primaryStage The primary stage for this application, where
     * the application scene can be set. The stage represents the main window of
     * the application.
     */
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInFXML.fxml"));
        Parent root = loader.load();
        LogInController controller = loader.getController();
        controller.initStage(primaryStage); 
        primaryStage.setTitle("LogIn");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
