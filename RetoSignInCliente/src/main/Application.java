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
