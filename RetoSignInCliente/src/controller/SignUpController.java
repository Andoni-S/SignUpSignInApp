/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class SignUpController {
    /**
     * Application stage.
    */
    private Stage stage;
    /**
     * Stage setter.
     * @param stage the stage to set
    */
    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnCancelar;
    
    @FXML
    private TextField txtEmail;
   
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtContrasena;
    
    @FXML
    private TextField txtConfirmar;
    
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
            //Deshabilitar el botón “Registrar”.
            btnRegistrar.setDisable(true);
            //Establecer el foco en el primer campo del orden de tabulación.
            txtEmail.requestFocus();
            //Establecer el botón Registrar como Default Button mediante setDefaultButton y el botón Cancelar cómo Cancel Button mediante setCancelButton.
            btnRegistrar.setDefaultButton(true);
            btnCancelar.setCancelButton(true);
            //Mostrar la ventana.
            stage.show();
 
            //Listeners
            txtEmail.textProperty().addListener(this::handleOnTextNotEmpty);
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
     * Checks if a TextField is empty and enables the button Registrar, if its empty, disable the button.
     * @param observable The observable value associated with the TextField's
     * text property.
     */
    public void handleOnTextNotEmpty(Observable observable) {
        try {
            if (txtEmail.getText().trim().isEmpty()) {
                btnRegistrar.setDisable(false);
            } else {
                btnRegistrar.setDisable(true);
                throw new Exception("Empty fields.");
            }
        } catch (Exception e){
            this.showErrorAlert(e);
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
