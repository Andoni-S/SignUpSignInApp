/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.EmailFormatException;
import factory.SignableFactory;
import java.io.IOException;
import java.util.regex.Pattern;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import libraries.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * This class contains the responses for behavior of the Log In view
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class LogInController {

    private Stage stage;

    @FXML
    private Label lblEmail, lblPassword, lblError;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pwdPassword;
    @FXML
    private Hyperlink hrefSignUp;
    @FXML
    private Button loginButton;

    /**
     * This method creates the Stage for this window.
     *
     * @param root
     */
    public void initStage(Parent root) {
        try {
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //TO-DO: mirar que se cargue la escena bien
            // Establish window title
            stage.setTitle("Iniciar sesión");
            // Window is not resizable
            stage.setResizable(false);
            // Disable 'Entrar' button
            loginButton.setDisable(true);
            // Establish the focus on the first field

            // Establish the 'Entrar' button as the default button
            loginButton.setDefaultButton(true);

            // Show the window
            stage.show();

            // Set control events handlers
            loginButton.setOnAction(this::handleLoginButtonAction);
            hrefSignUp.setOnAction(this::handleHrefSignupAction);
            txtEmail.textProperty().addListener(this::handleTextChange);
            pwdPassword.textProperty().addListener(this::handleTextChange);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent e) {
        try {
            // Validate that compulsory fields are not empty

            // Handle the login button click event here.
            String email = txtEmail.getText();
            String password = pwdPassword.getText();

            //Validate the format of the email, it must have a text before an '@' and a text before and after '.'
            //Pattern that must be respected
            String regexEmail = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$";
            Pattern patternEmail = Pattern.compile(regexEmail);
            //Validate if the pattern doesn't match the txtEmail field text
            if (!patternEmail.matcher(txtEmail.getText()).matches()) {
                throw new EmailFormatException("This email Format is not correct");
            }

            // Create a User object with the provided data
            User user = new User();
            user.setLogin(email);
            user.setPassword(password);

            // Send the User created to the logic Tier and recieve a full informed User
            //User mainWindowUser = new User();
            //mainWindowUser = SignableFactory.getSignable().logIn(user);

            // Open a MainWindow instance and close this window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindowFXML.fxml"));
            Parent root = (Parent) loader.load();
            MainWindowController mainWindowController = loader.getController();
            mainWindowController.initStage(root, user);

            stage.close();

        } catch (EmailFormatException ex) { //de momento está puesta una exception generica pero aqui deberian saltar las excepciones de conexion a la base de datos y de credenciales
            showError(ex.getMessage());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleTextChange(Observable observable) {
        try {
            String email = txtEmail.getText();
            String password = pwdPassword.getText();
            /*
            if (!email.trim().isEmpty() && !password.trim().isEmpty()) {
                // Enable 'Entrar' button
                loginButton.setDisable(false);
                // throw EmptyFieldException
            } else {
                // Disable 'Entrar' button
                loginButton.setDisable(true);
            }
             */
            if (email.trim().isEmpty() || password.trim().isEmpty()) {
                // Disable 'Entrar' button
                loginButton.setDisable(true);

            } else {
                // Enable 'Entrar' button
                loginButton.setDisable(false);
            }

        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void handleHrefSignupAction(ActionEvent e) {
        try {
            // Show the Sign Up window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUpFXML.fxml"));
            Parent root = (Parent) loader.load();
            SignUpController signupcontroller = ((SignUpController) loader.getController());
            signupcontroller.initStage(root);

            // Close this window
            stage.close();
        } catch (IOException ex) {
            // Gestionar la exception
        }
    }
    
    private void showError(String e){
        //Show error message
        lblError.setText(e);
    }
    
}
