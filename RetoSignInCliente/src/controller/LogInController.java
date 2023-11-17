package controller;

import exceptions.CredentialsException;
import exceptions.EmailFormatException;
import exceptions.PasswordFormatException;
import factory.SignableFactory;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.TranslateTransition;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import libraries.Signable;
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
    private Label lblError;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pwdPassword;
    @FXML
    private Hyperlink hrefSignUp;
    @FXML
    private Button loginButton;
    @FXML
    private Rectangle rectangle;
    @FXML
    private ImageView odooIcon;

    // Instance of the SignableImplementation object
    private final Signable signable = SignableFactory.getSignable();

    private TranslateTransition translateTransition;
    private TranslateTransition translateTransition2;

    /**
     * Initializes the Log In window stage with specified settings, controls,
     * and event handlers.
     *
     * @param root The root Parent node of the Log In window scene.
     */
    public void initStage(Parent root) {
        try {
            initializeRectangleAnim(); // This creates the animation for the login windown
            LOGGER.info("Initializing stage...");
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            // Establish window title
            stage.setTitle("Iniciar sesión");
            // Window is not resizable
            stage.setResizable(false);
            // Disable 'Entrar' button
            loginButton.setDisable(true);
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
            alert.showAndWait(); // Shows the alert and waits for the user response 
            LOGGER.severe("Exception during initialization: " + ex.getMessage());
        }
    }

    /**
     * Creates and initializes the animation for the Log In window.
     */
    public void initializeRectangleAnim() {
        // Establish the animation
        translateTransition = new TranslateTransition(Duration.seconds(1.8), rectangle);
        translateTransition.setFromX(-450); // Final position outside the screen
        translateTransition.setToX(0); // Final position inside the screen

        translateTransition2 = new TranslateTransition(Duration.seconds(3), odooIcon);
        translateTransition2.setFromY(-600); // Final position outside the screen
        translateTransition2.setToY(0); // Final position inside the screen
        // Starts the animation
        translateTransition.play();
        translateTransition2.play();
    }

    /**
     * Getter for the Log In window stage.
     *
     * @return The Log In window stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Setter for the Log In window stage.
     *
     * @param stage The new stage to be set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Event handler for the 'Log In' button action.
     *
     * This method is triggered when the 'Log In' button is pressed. It
     * validates the format of the entered email and password, creates a User
     * object with the provided credentials, sends the User to the logic tier
     * for authentication, and opens the main window upon successful login. If
     * there are validation errors or authentication fails, appropriate
     * exceptions are thrown and logged, and an error message is displayed.
     *
     * @param e The ActionEvent representing the 'Log In' button action.
     */
    @FXML
    private void handleLoginButtonAction(ActionEvent e) {
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
            // Add the provided data to the User
            User user = new User();
            user.setLogin(email);
            user.setPassword(password);

            // Send the User created to the logic Tier and recieve a full informed User
            User mainWindowUser = signable.logIn(user);

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

    /**
     * Event handler for text changes in the email and password fields.
     *
     * This method is responsible for validating the content of the email and
     * password fields, enabling or disabling the login button accordingly. If
     * both fields are non-empty, the 'Entrar' button is enabled; otherwise, it
     * is disabled. Additionally, it catches any exceptions that may occur
     * during the process and logs them using the class logger.
     *
     * @param o The Observable triggering the event (not used in the method
     * implementation).
     * @throws Exception If an unexpected error occurs during the handling of
     * text change.
     */
    @FXML
    private void handleTextChange(Observable o) {
        try {
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
        }
    }

    /**
     * Event handler for the 'Sign Up' hyperlink action.
     *
     * This method is triggered when the 'Sign Up' hyperlink is clicked. It logs
     * the event, loads the Sign Up window using an FXML loader, initializes the
     * corresponding controller, and closes the current window. Any IOException
     * that occurs during this process is caught and logged using the class
     * logger.
     *
     * @param e The ActionEvent representing the 'Sign Up' hyperlink action.
     */
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
            LOGGER.severe("Exception during handling 'Sign Up' hyperlink: " + ex.getMessage());
        }
    }

    /**
     * Displays an error message and logs it as a warning.
     *
     * This method takes an error message as input, logs it as a warning using
     * the class logger, and sets the same message to be displayed in a UI label
     * (lblError). This is commonly used for presenting user-friendly error
     * messages in the application.
     *
     * @param e The error message to be displayed and logged.
     */
    private void showError(String e) {
        LOGGER.warning("Error message displayed: " + e);
        //Show error message
        lblError.setText(e);
    }

    /**
     * Event handler for the window close request.
     *
     * This method is triggered when a user attempts to close the window. It
     * logs the close request, creates a confirmation dialog asking the user to
     * confirm the closure, and handles the closure based on the user's
     * response. If the user confirms, the window is closed; otherwise, the
     * closure is canceled, and the window remains open. The method consumes the
     * event to prevent the default window close behavior.
     *
     * @param event The WindowEvent representing the window close request.
     */
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
