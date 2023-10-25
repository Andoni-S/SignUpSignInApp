/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.ConditionalFeature.FXML;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class LogInController implements Initializable {

    @FXML
    private Button loginButton;
    private Stage stage; // Store a reference to the Stage

    public void initStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // You can initialize your controller here.
    }

    @FXML
    private void handleLoginButtonAction() {
        // Handle the login button click event here.
        // You can use 'stage' to manipulate the window (e.g., close it, show a new scene, etc.).
    }

}
