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
import controller.LogInController;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.SignableImplementation;

/**
 *
 * @author 2dam
 */

public class Application extends javafx.application.Application{    
    private final static java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(SignableImplementation.class.getName());
    /**
     * This method is called when the JavaFX application is launched. It is used
     * to initialize the primary stage (the main window) and set up the user
     * interface of the application.
     *
     * @param primaryStage The primary stage for this application, where the
     * application scene can be set. The first stage represents the Log In Window of the
     * application.
     */
    @Override
    public void start(Stage primaryStage) {    
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInFXML.fxml"));
            Parent root = loader.load();
            LogInController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.initStage(root);
            primaryStage.show();
            LOGGER.info("Log in window set.");
        } catch (IOException ex) {
           LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.info("Application initiated.");
        launch(args);
    }
}
