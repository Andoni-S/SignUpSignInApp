/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static javafx.application.Application.launch;
import controller.LogInController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;


/**
 *
 * @author 2dam
 */

public class Application extends javafx.application.Application{    
    /**
     * This method is called when the JavaFX application is launched. It is used
     * to initialize the primary stage (the main window) and set up the user
     * interface of the application.
     *
     * @param primaryStage The primary stage for this application, where the
     * application scene can be set. The stage represents the main window of the
     * application.
     */
    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInFXML.fxml"));
        Parent root = ((Parent)loader.load());
        LogInController controller = ((LogInController)loader.getController());
        controller.setStage(primaryStage);
        controller.initStage(root);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}