/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.ConfirmPasswordException;
import exceptions.EmailAlreadyExistsException;
import exceptions.EmailFormatException;
import exceptions.EmptyFieldException;
import exceptions.PasswordFormatException;
import exceptions.ServerErrorException;
import factory.SignableFactory;
import java.io.IOException;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import libraries.User;

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
    private PasswordField pwdContrasena;
    
    @FXML
    private TextField txtConfirmar;
    
    @FXML
    private PasswordField pwdConfirmar;
    
    @FXML
    private TextField txtCodigoPostal;
    
    @FXML
    private TextField txtTelefonoMovil;
    
    @FXML
    private TextField txtDireccion;
    
    @FXML
    private Label lblError;
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
      
    public void initStage(Parent root) {
        try {
            //TODO Revisar esto con la llamada desde LogInController
            //Establish the title of the window to "Register"
            stage.setTitle("Registro");
            //The window is not resizable
            stage.setResizable(false);
            //Disable the "Register" button
            btnRegistrar.setDisable(true);
            //Set focus on the first tab order field.
            txtEmail.requestFocus();
            //Set the "Registrar" button as the Default Button using setDefaultButton and the "Cancelar" button as the Cancel Button using setCancelButton.
            btnRegistrar.setDefaultButton(true);
            btnCancelar.setCancelButton(true);
            //Show the window.
            stage.show();
            
            //TODO JUAN
            //Listeners of handleOnTextNotEmpty
            StringProperty juan = txtNombre.textProperty();
            txtEmail.textProperty().addListener(this::handleOnTextNotEmpty);
            juan.addListener(this::handleOnTextNotEmpty);
            pwdContrasena.textProperty().addListener(this::handleOnTextNotEmpty);
            pwdConfirmar.textProperty().addListener(this::handleOnTextNotEmpty);
            //Listener of handleOnButtonClick
            btnRegistrar.setOnAction(this::handleOnButtonClick);
        } catch (Exception e) {
             this.showErrorAlert(e.getMessage());
        }
    }
    //Validate if the mandatory fields are filled in.
    public void handleOnButtonClick(ActionEvent event) {
        try {
            User newUser = new User();
            newUser.setLogin(txtEmail.getText());
            newUser.setName(txtNombre.getText());
            newUser.setPassword(txtConfirmar.getText());
            //Check if the TextFields are empty
            if (!txtCodigoPostal.getText().trim().isEmpty()){
                newUser.setPostalCode(txtCodigoPostal.getText());
            }
            if (!txtTelefonoMovil.getText().trim().isEmpty()){
                newUser.setMobilePhone(txtTelefonoMovil.getText());
            }
            if (!txtDireccion.getText().trim().isEmpty()){
                newUser.setStreet(txtDireccion.getText());
            }

            //Validate the format of the password, it must have at least 4 characters
            if (pwdContrasena.getText().length() < 4){
                throw new PasswordFormatException("The password must be at least 4 characters long.");
            }
            //TODO Validar del todo jaja
            //Validate the format of the email, it must have an '@' and a '.'
            if (!txtEmail.getText().contains("@") || !txtEmail.getText().contains(".")){
                throw new EmailFormatException("The email does not have a correct format.");
            }
            //Register the user, if it already exists, it will throw an EmailAlreadyExistsException
            User userServer = SignableFactory.getSignable().signUp(newUser);
            
            
        } catch (EmailAlreadyExistsException e) {
            this.showErrorAlert(e.getMessage());
        } catch (EmailFormatException e) {
            this.showErrorAlert(e.getMessage());
        } catch (PasswordFormatException e) {
            this.showErrorAlert(e.getMessage());
        } catch (Exception e) {
            this.showErrorAlert(e.getMessage());
        }
    }

    /**
     * Checks if a TextField is empty and enables the button Registrar, if its
     * empty, disable the button.
     *
     * @param observable The observable value associated with the TextField's
     * text property.
     */
    public void handleOnTextNotEmpty(Observable observable) {
        try {
            //TODO No entra al handler
            //Validate if one of the fields is empty
            System.out.println("holaaaaaaaaaaaa");
            System.out.println(txtNombre.getText().isEmpty());
            System.out.println(txtEmail.getText().isEmpty());
            System.out.println(pwdContrasena.getText().isEmpty());
            System.out.println(pwdConfirmar.getText().isEmpty());
            if (!txtNombre.getText().isEmpty()  && !txtEmail.getText().isEmpty()  && !pwdContrasena.getText().isEmpty() && !pwdConfirmar.getText().isEmpty()) {
                //Disable the button
                System.out.println("Jaja deberia activarse el boton");
                btnRegistrar.setDisable(false);  
               
            } else {
                //Enable the button
                System.out.println("Jaja vacio");
                btnRegistrar.setDisable(true); 
                throw new EmptyFieldException("A field is empty.");
            }
        } catch (EmptyFieldException e) {
            this.showErrorAlert(e.getMessage());
        } catch (Exception e) {
            this.showErrorAlert(e.getMessage());
        }
    }

    /**
     * This handler checks if both PasswordFields are equal.
     *
     * @param observable
     */
    public void handleOnCheckPassword(Observable observable, String length) {
        try {
            //Validate if the password TextField and the confirmation have the same text
            if (!pwdContrasena.getText().equals(pwdConfirmar.getText())) {
                throw new ConfirmPasswordException("Password must be the same.");
            }
        } catch (ConfirmPasswordException e) {
            this.showErrorAlert(e.getMessage());
        } catch (Exception e) {
            this.showErrorAlert(e.getMessage());
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
    private void showErrorAlert(String e) {
        //TODO Cambiar por la label
        lblError.setText(e);
    }
}
