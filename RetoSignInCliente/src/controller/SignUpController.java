/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.ConfirmPasswordException;
import exceptions.EmailAlreadyExistsException;
import exceptions.EmailFormatException;
import exceptions.MaxCharException;
import exceptions.NameException;
import exceptions.NumericException;
import exceptions.PasswordFormatException;
import exceptions.ServerErrorException;
import factory.SignableFactory;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import libraries.Signable;
import libraries.User;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class SignUpController {
    /**
     * Application stage.
    */
    private Stage stage;
    /**
     * Instance of the signable implementation which implements the logic
     */
    Signable sign = SignableFactory.getSignable();
    /**
     * Logger
     */
    private final static Logger LOGGER = Logger.getLogger(SignUpController.class.getName());
    /**
     * Register button
     */
    @FXML
    private Button btnRegistrar;
    /**
     * Cancel button
     */
    @FXML
    private Button btnCancelar;
    /**
     * Email text field
     */
    @FXML
    private TextField txtEmail;
    /**
     * Name text field
     */
    @FXML
    private TextField txtNombreCompleto;
    /**
     * Password field
     */
    @FXML
    private PasswordField pwdContrasena;    
    /**
     * Confirmation of password field
     */
    @FXML
    private PasswordField pwdConfirmar;
    /**
     * Postal code field
     */
    @FXML
    private TextField txtCodigoPostal;
    /**
     * Telephone number text field
     */
    @FXML
    private TextField txtTelefonoMovil;
    /**
     * Address text field
     */
    @FXML
    private TextField txtDireccion;
    /**
     * Error label
     */
    @FXML
    private Label lblError;
    /**
     * Stage setter.
     * @param stage the stage to set
    */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * Initialization method of the Sign Up controller
     * @param root 
     */
    public void initStage(Parent root) {
        try {
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
            
            //Listeners of handleOnTextNotEmpty 
            txtEmail.textProperty().addListener(this::handleOnTextNotEmpty);
            txtNombreCompleto.textProperty().addListener(this::handleOnTextNotEmpty);
            pwdContrasena.textProperty().addListener(this::handleOnTextNotEmpty);
            pwdConfirmar.textProperty().addListener(this::handleOnTextNotEmpty);
            //Listener of handleOnButtonClick
            btnRegistrar.setOnAction(this::handleOnButtonClick);
            //Listener of handleOnCancelClick
            btnCancelar.setOnAction(this::handleOnCancelButton);
            
            LOGGER.info("SignUp window initialized.");
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            showError("An unexpected error occurred.");
        }
    }
    /**
     * Handler to validate the information before sending it
     * @param event 
     */
    public void handleOnButtonClick(ActionEvent event) {
        try {   
            LOGGER.info("Register button clicked.");         
            //Validate the format of the email, it must have a text before an '@' and a text before and after '.'
            //Pattern that must be respected
            String regexEmail = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$";
            Pattern patternEmail = Pattern.compile(regexEmail);
            //Validate if the pattern doesn't match the txtEmail field text
            if (!patternEmail.matcher(txtEmail.getText()).matches()){
                throw new EmailFormatException("The email doesn't have a correct format.");
            }
            LOGGER.info("Email format validated.");  
            //Validate the format of the name. Must be alphabetic and must have at least two words.
            //Pattern that must be respected
            String regexName="^[A-Za-z]+( [A-Za-z]+)$";
            Pattern patternName = Pattern.compile(regexName);
            //Validate if the name doesn't have the appropiate format
            if (!patternName.matcher(txtNombreCompleto.getText()).matches()){
                throw new NameException("The name must include a surname.");
            }
            LOGGER.info("Name format validated.");
            //Validate the format of the password, it must have at least 4 characters
            if (pwdContrasena.getText().length() < 4){
                throw new PasswordFormatException("The password must be at least 4 characters long.");
            }
            LOGGER.info("Password format validated.");
            //Validate if the password field and the confirmation field have the same text
            if (!pwdContrasena.getText().equals(pwdConfirmar.getText())) {
                throw new ConfirmPasswordException("Password must be the same.");
            }       
            LOGGER.info("Password confirmation validated.");
            //Creating a new User to send back to the Server with its proper attributes
            User newUser = new User(); 
            newUser.setLogin(txtEmail.getText());          
            newUser.setName(txtNombreCompleto.getText());          
            newUser.setPassword(pwdConfirmar.getText());
            LOGGER.info("User created and set.");
            //Validate if the TextField is empty
            if (!txtCodigoPostal.getText().trim().isEmpty()){  
                //Validate the format of the postal code. Must be numeric and be 5 character long.
                if (txtCodigoPostal.getText().length() > 5){
                    throw new MaxCharException("5 character limit reached.");
                }
                //Pattern that must be respected
                String regexCod="^[0-9]+$";
                Pattern patternCod = Pattern.compile(regexCod);
                //Validate the format of the postal code
                if (!patternCod.matcher(txtCodigoPostal.getText()).matches()){
                    throw new NumericException("The code must only be numeric.");
                }
                //Setting the postal code for the user
                newUser.setPostalCode(txtCodigoPostal.getText());
            }
            LOGGER.info("Postal code format validated and set in the User.");

            //Validate if the TextField is empty
            if (!txtTelefonoMovil.getText().trim().isEmpty()){
                //Validate the format of the telephone number. Must be numeric and be 9 character long.
                if (txtTelefonoMovil.getText().length() > 9){
                    throw new MaxCharException("9 character limit reached.");
                }
                //Pattern that must be respected
                String regexCod="^[0-9]+$";
                Pattern patternCod = Pattern.compile(regexCod);
                //Validate the format of the telephone number
                if (!patternCod.matcher(txtTelefonoMovil.getText()).matches()){
                    throw new NumericException("The code must only be numeric.");
                }
                //Setting the telephone number for the user
                newUser.setMobilePhone(txtTelefonoMovil.getText());
            }
            LOGGER.info("Telephone number format validated and set in the User.");                

            //Validate if the TextField is empty
            if (!txtDireccion.getText().trim().isEmpty()){
                //Setting the street address for the user
                newUser.setStreet(txtDireccion.getText());
            }
            LOGGER.info("Address set in the User.");
            //Register the user, if it already exists, it will throw an EmailAlreadyExistsException
            User userServer = sign.signUp(newUser); 
            LOGGER.info("User profile correctly set.");
            //Show the MainWindow window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            Parent root = loader.load();
            MainWindowController mainWindowController = loader.getController();
            mainWindowController.initStage(root, userServer);
            // Close this window
            stage.close();
            LOGGER.info("SignUp window closed and MainWindow initialized.");
        } catch (ConfirmPasswordException | EmailFormatException | PasswordFormatException e) {
            LOGGER.warning(e.getMessage());
            showError(e.getMessage());
        } catch (EmailAlreadyExistsException e) {
            LOGGER.warning(e.getMessage());
            showError("This email already exists.");
        } catch (ServerErrorException e) {
            LOGGER.severe(e.getMessage());
            showError("A server error occurred. Please, try later.");
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            showError(e.getMessage());
        }
    }

    /**
     * Handle to validate if a TextField is empty and enabling the "Registrar" button, if its
     * empty, disable the button.
     *
     * @param observable The observable value associated with the TextField's
     * text property.
     */
    public void handleOnTextNotEmpty(Observable observable) {
        try {
            //Validate if one of the fields is empty
            if (!txtEmail.getText().trim().isEmpty() && !txtNombreCompleto.getText().trim().isEmpty() && !pwdContrasena.getText().trim().isEmpty() && !pwdConfirmar.getText().trim().isEmpty()) {
                //We clean the error label
                lblError.setText("");
                //All the text fields are complete, we enable the button
                btnRegistrar.setDisable(false);         
                LOGGER.info("Fields not empty.");
            } else {
                //Disable the button
                btnRegistrar.setDisable(true); 
            }   
            //Validate if the characters' max length is reached
            if(txtEmail.getText().length() > 300 || txtNombreCompleto.getText().length() > 300 || pwdContrasena.getText().length() > 300 || pwdConfirmar.getText().length() > 300) {
                //Disable the button
                btnRegistrar.setDisable(true);
                throw new MaxCharException("300 character limit reached.");
            } else {
                //Enable the button
                btnRegistrar.setDisable(false);
            } 
        } catch (MaxCharException e){
            LOGGER.warning(e.getMessage());
            showError(e.getMessage());
        } catch (Exception e) {
            LOGGER.warning("An unexpected error occurred.");
            showError("An unexpected error occurred.");
        }
    }
    /**
     * This method will handle the Cancel button ActionEvent and if the user accepts the alert, go back to the LogIn window
     * @param event 
     */
    public void handleOnCancelButton(ActionEvent event){
        try {   
            LOGGER.info("Cancel button pressed.");
            //Show an alert to confirm going to the Log in window
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to exit?");
            Optional<ButtonType> action = alert.showAndWait();
            LOGGER.info("Showed alert.");
            //If the user selects the confirmation button of the alert
            if(action.get() == ButtonType.OK){
                LOGGER.info("Confirmation button selected.");
                // Show the LogIn window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInFXML.fxml"));
                Parent root = loader.load();
                LogInController logInController = loader.getController();      
                logInController.initStage(root);
                //Close this window
                stage.close();
                LOGGER.info("SignUp window closed and LogIn initialiazed.");
            }
        } catch (Exception ex) {
            LOGGER.warning("An unexpected error occurred.");
            showError("An unexpected error occurred.");
        }
    
    
    }
    /**
     * Sets the text of a label to inform the user about the occurred exception
     *
     * @param e The exception to display in the alert.
     */
    private void showError(String e) {
        //Showing error message
        LOGGER.info("Error label text changed.");
        lblError.setText(e);
    }
}
