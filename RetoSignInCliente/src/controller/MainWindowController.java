package controller;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import libraries.User;
/**
 *
 * @author Andoni Sanz
 */
public class MainWindowController {
    /**
     * Application stage.
    */
    private Stage stage;
    /**
     * Stage setter.
     * @param stage the stage to set
    */
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblPostalCode;
    @FXML
    private Label lblName;
    @FXML
    private Label lblPhone;
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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
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
        } catch (Exception e) {
             this.showErrorAlert(e);
        }
    }
    @FXML
    void handleBtnClose(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInFXML.fxml"));
            Parent root = loader.load();
            LogInController controller = loader.getController();
            Stage parentStage = new Stage();
            controller.setStage(parentStage);
            controller.initStage(root);
            parentStage.show();
            // Obtenemos el escenario (Stage) actual a través del botón
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            // Cerramos la ventana
            stage.close();
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     private void handleCloseRequest(WindowEvent event) {
        // Obtenemos el escenario (Stage) actual a través del botón
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        // Cerramos la ventana
        stage.close();
    }
    public void handleOnButtonClick(Observable observable) {
        try {
        } catch(Exception e){
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
    private void showErrorAlert(Exception e) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setContentText(e.toString());
        alert.showAndWait();
    }
}