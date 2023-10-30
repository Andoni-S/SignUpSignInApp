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

package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainWindowController;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) {
        try {
   
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            Parent root = loader.load();
            MainWindowController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.initStage(root);
            //primaryStage.setTitle("LogIn");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
