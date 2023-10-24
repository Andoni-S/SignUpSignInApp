package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainWindow.fxml"));
            
            // Create the main window
            Scene scene = new Scene(root, 800, 600);
            
            // Set the title of the main window
            primaryStage.setTitle("JavaFX Main Window");
            
            // Set the scene for the primary stage
            primaryStage.setScene(scene);
            
            // Show the main window
            primaryStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
