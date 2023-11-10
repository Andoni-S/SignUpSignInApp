package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import libraries.User;

/**
 *
 * Controller class for the Main Window of the application. This class handles
 * user interactions, window initialization, and event handling. The Main Window
 * displays user information and provides a logout option.
 *
 * @author Andoni Sanz
 */
public class MainWindowController {

    private Stage stage;
    private final static Logger LOGGER = Logger.getLogger(SignUpController.class.getName());

    @FXML
    private TextField textEmail;
    @FXML
    private TextField textName;
    @FXML
    private TextField textPhone;
    @FXML
    private TextField textAddress;
    @FXML
    private TextField textPostalCode;
    @FXML
    private Button btnLogout;
    private User user;

    /**
     * Getter for the User associated with the Main Window.
     *
     * @return The User associated with the Main Window.
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for the User associated with the Main Window.
     *
     * @param user The User to set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter for the Stage of the Main Window.
     *
     * @param stage The Stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the Main Window stage with specified settings and user
     * information.
     *
     * @param root The root Parent node of the Main Window scene.
     * @param newUser The User object associated with the Main Window.
     */
    public void initStage(Parent root, User newUser) {
        try {
            //Set scene and view DOM root
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            //Establecer el título de la ventana al valor “Registro.”
            stage.setTitle("Registro");
            //La ventana no es redimensionable.
            stage.setResizable(false);
            //Establecer el foco en el primer campo del orden de tabulación.
            textEmail.requestFocus();
            //Establecer el botón Registrar como Default Button mediante setDefaultButton y el botón Cancelar cómo Cancel Button mediante setCancelButton.
            btnLogout.setDefaultButton(true);
            //Mostrar la ventana.
            user = newUser;
            stage.setOnCloseRequest(event -> handleCloseRequest(event));
            btnLogout.setDefaultButton(true);
            textEmail.setEditable(false);
            textName.setEditable(false);
            textPhone.setEditable(false);
            textAddress.setEditable(false);
            textPostalCode.setEditable(false);
            textEmail.setText(user.getLogin());
            textName.setText(user.getName());
            textPhone.setText(user.getMobilePhone());
            textAddress.setText(user.getAddress());
            textPostalCode.setText(user.getPostalCode());
            stage.show();

            LOGGER.info("Main Window initialized.");
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            this.showErrorAlert(e);
        }
    }

    /**
     * Handles the "Cerrar Sesión" button click event. Closes the current window
     * and opens the Log In window.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void handleBtnClose(ActionEvent event) {
        try {
            LOGGER.info("Login button clicked.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInFXML.fxml"));
            Parent root = loader.load();
            LogInController controller = loader.getController();
            Stage parentStage = new Stage();
            controller.setStage(parentStage);
            controller.initStage(root);
            parentStage.show();
            LOGGER.info("LogInWindow initialized.");
            // Obtenemos el escenario (Stage) actual a través del botón
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            // Cerramos la ventana
            stage.close();
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the window close request event. Closes the current window.
     *
     * @param event The WindowEvent representing the window close request.
     */
    private void handleCloseRequest(WindowEvent event) {
        // Obtenemos el escenario (Stage) actual a través del botón
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        // Cerramos la ventana
        stage.close();
        LOGGER.info("Closing the window.");
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
        LOGGER.info("Alert shown.");
        Alert alert = new Alert(AlertType.WARNING);
        alert.setContentText(e.toString());
        alert.showAndWait();
    }
}
