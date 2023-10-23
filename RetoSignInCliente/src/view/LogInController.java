/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static javafx.application.ConditionalFeature.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

/**
 * This class contains the responses for behavior of the Log In view
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class LogInController {

    private Stage stage;

    @FXML
    private Label lblEmail, lblPassword;
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
     * @param Stage
     */
    public void initStage(Parent root) {
        try {
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

            // Set control events handlers
            loginButton.setOnAction(this::handleLoginButtonAction);
            hrefSignUp.setOnAction(this::handleHrefSignupAction);
            txtEmail.addEventHandler(InputMethodEvent.INPUT_METHOD_TEXT_CHANGED, this::handleTextChange);
            pwdPassword.addEventHandler(InputMethodEvent.INPUT_METHOD_TEXT_CHANGED, this::handleTextChange);

        } catch (Exception ex) {

        }
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent e) {
        try {
            // Validate that compulsory fields are not empty

            // Handle the login button click event here.
            String email = txtEmail.getText();
            String password = pwdPassword.getText();

            // Create a User object with the entered data
            User user = new User();
            user.setLogin(email);
            user.setPassword(password);

        } catch (Exception ex) { //de momento está puesta una exception generica pero aqui deberian saltar las excepciones de conexion a la base de datos y de credenciales
            ex.getMessage();
        }
    }

    @FXML
    private void handleTextChange(InputMethodEvent e) {
        try {
            String email = txtEmail.getText();
            String password = pwdPassword.getText();

            if (email.trim().isEmpty() || password.trim().isEmpty()) {
                // Disable 'Entrar' button
                loginButton.setDisable(true);
                // throw EmptyFieldException
            }

            // Enable 'Entrar' button
            loginButton.setDisable(false);

        } catch (Exception ex) {

        }
    }

    @FXML
    private void handleHrefSignupAction(ActionEvent e) {
        // Show the Register window
        //view.SignUpController.initStage(Parent root);
        // Close this window
        stage.close();
    }

}
