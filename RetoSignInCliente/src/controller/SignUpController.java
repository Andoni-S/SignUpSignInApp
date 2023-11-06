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
import java.util.regex.Pattern;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import libraries.Signable;
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
    Signable sign = SignableFactory.getSignable();
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNombreCompleto;
    @FXML
    private PasswordField pwdContrasena;
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
    /**
     * Stage setter.
     * @param stage the stage to set
    */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void initStage(Parent root) {
        try {
            //TODO Revisar esto con la llamada desde LogInController
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
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
        } catch (Exception e) {
             this.showErrorAlert("An unexpected error occurred.");
        }
    }
    //Validate if the mandatory fields are filled in.
    public void handleOnButtonClick(ActionEvent event) {
        try {
            //Validate the format of the password, it must have at least 4 characters
            if (pwdContrasena.getText().length() < 4){
                System.out.println("Compruebo contraseña");
                throw new PasswordFormatException("The password must be at least 4 characters long.");
            }
            //Validate if the password field and the confirmation have the same text
            if (!pwdContrasena.getText().equals(pwdConfirmar.getText())) {
                throw new ConfirmPasswordException("Password must be the same.");
            }
            //Validate the format of the email, it must have a text before an '@' and a text before and after '.'
            //Pattern that must be respected
            String regexEmail = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$";
            Pattern patternEmail = Pattern.compile(regexEmail);
            //Checks if the pattern doesn't match the txtEmail field text
            if (!patternEmail.matcher(txtEmail.getText()).matches()){
                throw new EmailFormatException("The email doesn't have a correct format.");
            }
            //Validate the format of the name. Must be alphabetic and must have at least two words.
            String regexName="^[A-Za-z]+( [A-Za-z]+)$";
            Pattern patternName = Pattern.compile(regexName);
            if (!patternName.matcher(txtNombreCompleto.getText()).matches()){
                throw new NameException("The name must include a surname.");
            }
            //Creating a new User to send back to the Server with its proper attributes
            User newUser = new User();
            newUser.setLogin(txtEmail.getText());
            newUser.setName(txtNombreCompleto.getText());
            newUser.setPassword(pwdConfirmar.getText());
            //Check if the TextFields are empty
            if (!txtCodigoPostal.getText().trim().isEmpty()){
                //Validate the format of the postal code. Must be numeric and be 5 character long.
                if (txtCodigoPostal.getText().length() > 5){
                    throw new MaxCharException("5 character limit reached.");
                }
                String regexCod="^[0-9]+$";
                Pattern patternCod = Pattern.compile(regexCod);
                if (!patternCod.matcher(txtCodigoPostal.getText()).matches()){
                    throw new NumericException("The code must only be numeric.");
                }
                //Setting the postal code for the user
                newUser.setPostalCode(txtCodigoPostal.getText());
            }
            if (!txtTelefonoMovil.getText().trim().isEmpty()){
                //Validate the format of the telephone number. Must be numeric and be 9 character long.
                if (txtCodigoPostal.getText().length() > 9){
                    throw new MaxCharException("9 character limit reached.");
                }
                    String regexCod="^[0-9]+$";
                    Pattern patternCod = Pattern.compile(regexCod);
                    if (!patternCod.matcher(txtCodigoPostal.getText()).matches()){
                        throw new NumericException("The code must only be numeric.");
                    }
                //Setting the telephone number for the user
                newUser.setMobilePhone(txtTelefonoMovil.getText());
            }
            if (!txtDireccion.getText().trim().isEmpty()){
                //Setting the street address for the user
                newUser.setAddress(txtDireccion.getText());
            }
            //Register the user, if it already exists, it will throw an EmailAlreadyExistsException
            User userServer = sign.signUp(newUser);
            // Show the Sign Up window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            Parent root = loader.load();
            MainWindowController mainWindowController = loader.getController();
             Stage parentStage = stage;
            mainWindowController.initStage(root, newUser);
            // Close this window
            stage.close();
        } catch (ConfirmPasswordException | EmailFormatException | PasswordFormatException e) {
            this.showErrorAlert(e.getMessage());
        } catch (EmailAlreadyExistsException e) {
            this.showErrorAlert("This email already exists.");
        } catch (ServerErrorException e) {
            this.showErrorAlert("A server error occurred. Please, try later.");
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
            //Validate if one of the fields is empty
            if (!txtEmail.getText().trim().isEmpty() && !txtNombreCompleto.getText().trim().isEmpty() && !pwdContrasena.getText().trim().isEmpty() && !pwdConfirmar.getText().trim().isEmpty()) {
                //We clean the error label
                lblError.setText("");
                //Enable the button
                btnRegistrar.setDisable(false);
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
        } catch (Exception e) {
            this.showErrorAlert("An unexpected error occurred.");
        }
    }
    public void handleOnCancelButton(Observable observable){
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
        //Showing error message
        lblError.setText(e);
    }
}