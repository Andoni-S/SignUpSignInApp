package controller;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
 * @author 2dam
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
    public void initStage(Parent root, User user) {
        try {
            //Set scene and view DOM root
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            //Establecer el título de la ventana al valor “Registro.”
            stage.setTitle("Ventana principal");
            //La ventana no es redimensionable.
            stage.setResizable(false);
            //Establecer el foco en el primer campo del orden de tabulación.
            textEmail.requestFocus();
            //Establecer el botón Registrar como Default Button mediante setDefaultButton y el botón Cancelar cómo Cancel Button mediante setCancelButton.
            btnLogout.setDefaultButton(true);
            //We save the user passed from the previous controller
            this.user = user;
            stage.setOnCloseRequest(event -> handleCloseRequest(event));
            btnLogout.setDefaultButton(true);
            textEmail.setEditable(false);
            textName.setEditable(false);
            textPhone.setEditable(false);
            textAddress.setEditable(false);
            textPostalCode.setEditable(false);
            loadUser();
            //Mostrar la ventana.
            stage.show();
        } catch (Exception e) {
             this.showErrorAlert(e);
        }
    }
    public void loadUser(){
        textEmail.setText("andoni@gmail.com");
        //textEmail.setText(user.getLogin());
        textName.setText(user.getName());
        textPhone.setText(user.getMobilePhone());
        textAddress.setText(user.getStreet());
        textPostalCode.setText(user.getPostalCode());
    }
    @FXML
    void handleBtnClose(ActionEvent event) {
        // Obtenemos el escenario (Stage) actual a través del botón
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        // Cerramos la ventana
        stage.close();
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