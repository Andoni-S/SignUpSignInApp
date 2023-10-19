/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.ConditionalFeature.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import libraries.User;

/**
 * FXML Controller class
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class LogInController implements Initializable {

    private Stage stage;

    @FXML
    private Label lblEmail, lblPassword;
    @FXML
    private TextField txtEmail, pwdPassword;
    @FXML
    private Hyperlink hrefSignUp;
    @FXML
    private Button loginButton;

    public void initStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // You can initialize your controller here.

        loginButton.setOnAction(this::handleLoginButtonAction); //Set handleLogInButton as the action for the button
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        try {
            // Handle the login button click event here.
            String email = txtEmail.getText();
            String password = pwdPassword.getText();

            // Create a User object with the entered data
            User user = new User();
            user.setLogin(email);
            user.setPassword(password);
        } catch (Exception e){
            e.getMessage();
        }

        //espera a que salte el evento del boton
        //cuando recibe el evento recoge los campos informados de las ventanas
        //instancia un objeto User con la informacion recogida de los campos informados
        //manda el objeto User al Socket
        // You can use 'stage' to manipulate the window (e.g., close it, show a new scene, etc.).
    }

}
