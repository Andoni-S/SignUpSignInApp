/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Optional;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class MainWindowController {
    /**
     * Application stage.
    */
    private Stage stage;
    /**
     * Stage setter.
     * @param stage the stage to set
    */
@FXML
    private Label lblEmail;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblPostalCode;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPhone;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textName;

    @FXML
    private TextField textPhone;

    @FXML
    private TextField textAddress;

    @FXML
    private TextField textPostalCode;
    
    @FXML
    private Button btnLogout;
      
    public void setStage(Stage stage) {
        this.stage = stage;
    }
      
    public void initStage(Parent root) {
        try {
            //Set scene and view DOM root
            Scene scene = new Scene(root);
            stage = new Stage();
            //Establecer el título de la ventana al valor “Registro.”
            stage.setTitle("Registro");
            //La ventana no es redimensionable.
            stage.setResizable(false);
            //Establecer el foco en el primer campo del orden de tabulación.
            textEmail.requestFocus();
            //Establecer el botón Registrar como Default Button mediante setDefaultButton y el botón Cancelar cómo Cancel Button mediante setCancelButton.
            btnLogout.setDefaultButton(true);
            //Mostrar la ventana.
            stage.show();
           
        } catch (Exception e) {
             this.showErrorAlert(e);
        }
    }

    public void handleOnButtonClick(Observable observable) {
        try {
           
        } catch(Exception e){
        
        }
    }
    /**
     * Displays a warning alert dialog with the provided exception message.
     *
     * @param e The exception to display in the alert.
     * @implNote This method creates a modal alert dialog of the type
     * AlertType#WARNING. The content of the alert is set to the string
     * representation of the provided exception.
     */
    private void showErrorAlert(Exception e) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setContentText(e.toString());
        alert.showAndWait();
    }

}
