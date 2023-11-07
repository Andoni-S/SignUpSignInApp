/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.CredentialsException;
import exceptions.EmailFormatException;
import exceptions.PasswordFormatException;
import factory.SignableFactory;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import libraries.User;

/**
 * This class contains the responses for behavior of the Log In view
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class LogInController {

    private final static Logger LOGGER = Logger.getLogger(SignUpController.class.getName());
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
            LOGGER.info("Initializing stage...");
            Scene scene = new Scene(root);
            stage.setScene(scene);
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
            LOGGER.info("Log In Window initialized and shown");
            // Set control events handlers
            LOGGER.info("Setting control evetns handlers...");
            loginButton.setOnAction(this::handleLoginButtonAction);
            hrefSignUp.setOnAction(this::handleHrefSignupAction);
            txtEmail.textProperty().addListener(this::handleTextChange);
            pwdPassword.textProperty().addListener(this::handleTextChange);
            stage.setOnCloseRequest(event -> handleCloseRequest(event));
            LOGGER.info("Control events handlers set");
        } catch (Exception ex) {
            // Show an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Application Error");
            alert.setContentText("Failed to initialize the application. Please try restarting the app.");
            alert.showAndWait();
            LOGGER.severe("Exception during initialization: " + ex.getMessage());
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
        System.out.println("Logeando");
        try {
            LOGGER.info("Log In Button pressed...");
            // Handle the login button click event here.
            String email = txtEmail.getText();
            String password = pwdPassword.getText();
            //Validate the format of the email, it must have a text before an '@' and a text before and after '.'
            //Pattern that must be respected
            String regexEmail = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$";
            Pattern patternEmail = Pattern.compile(regexEmail);
            //Validate if the pattern doesn't match the txtEmail field text
            if (!patternEmail.matcher(txtEmail.getText()).matches()) {
                LOGGER.severe("Wrong email format");
                throw new EmailFormatException("El formato de las credenciales no es correcto");
            }
            //Validate the format of the password, it must be 8 characters long at minimum and contain a capital letter and a number
            //Pattern that must be respected
            String regexPassword = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
            Pattern patternPassword = Pattern.compile(regexPassword);
            //Validate if the pattern doesn't match the password field
            if (!patternPassword.matcher(pwdPassword.getText()).matches()) {
                LOGGER.severe("Wrong password format");
                throw new PasswordFormatException("El formato de las credenciales no es correcto");
            }
            // Create a User object with the provided data
            User user = new User();
            user.setLogin(email);
            user.setPassword(password);

            // Send the User created to the logic Tier and recieve a full informed User
            User mainWindowUser = new User();
            mainWindowUser = SignableFactory.getSignable().logIn(user);
            // Close this window and open a MainWindow
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindowFXML.fxml"));
            Parent root = loader.load();
            MainWindowController mainWindowController = loader.getController();
            Stage parentStage = new Stage();
            mainWindowController.setStage(parentStage);
            mainWindowController.initStage(root, mainWindowUser);

            stage.close();

        } catch (EmailFormatException | PasswordFormatException ex) {
            LOGGER.severe("Exception during login: " + ex.getMessage());
            showError("Error: " + ex.getMessage());
        } catch (CredentialsException ex) {
            LOGGER.severe("Credentials Exception: " + ex.getMessage());   
            showError("Error: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.severe("Exception during login: " + ex.getMessage());
            showError("Error: " + ex.getMessage());
        }
    }

    @FXML
    private void handleTextChange(Observable o) {
        try {
            LOGGER.info("Text change detected...");
            String email = txtEmail.getText();
            String password = pwdPassword.getText();

            if (!email.trim().isEmpty() && !password.trim().isEmpty()) {
                // Enable 'Entrar' button
                loginButton.setDisable(false);
                // throw EmptyFieldException
            } else {
                // Disable 'Entrar' button
                loginButton.setDisable(true);
            }

        } catch (Exception ex) {
            LOGGER.severe("Exception during text change: " + ex.getMessage());
            ex.getMessage();
        }
    }

    @FXML
    private void handleHrefSignupAction(ActionEvent e) {
        try {
            LOGGER.info("Hyperlink 'Sign Up' clicked...");
            // Show the Sign Up window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUpFXML.fxml"));
            Parent root = loader.load();
            SignUpController signupcontroller = loader.getController();
            signupcontroller.initStage(root);

            // Close this window
            stage.close();
        } catch (IOException ex) {
            // Gestionar la exception
            LOGGER.severe("Exception during handling 'Sign Up' hyperlink: " + ex.getMessage());
        }
    }

    private void showError(String e) {
        LOGGER.warning("Error message displayed: " + e);
        //Show error message
        lblError.setText(e);
    }

    private void handleCloseRequest(WindowEvent event) {
        LOGGER.info("Window close requested...");
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Closure");
        alert.setHeaderText("Are you sure you want to close the window?");
        // Add confirmation and cancel buttons to the dialog
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
        // Show the dialog and wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirmButton) {
            // If the user confirms, close the window
            event.consume(); // Stops the default window close
            LOGGER.info("Window closed by user confirmation.");
            stage.close();
        } else {
            // If the user cancels, do not close the window
            event.consume(); // Stops the default window close
            LOGGER.info("Window closure canceled by user.");
        }
    }
}
