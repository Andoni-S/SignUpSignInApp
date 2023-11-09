package controller;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import main.Application;
import java.util.Random;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Pruebas de interfaz de usuario utilizando TestFX para SignUpController.
 * @author Jagoba Bartolomé Barroso
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSignUpController extends ApplicationTest {
    // Declaring JavaFX components for UI testing.
    private TextField txtEmail;
    private TextField txtNombreCompleto;
    private PasswordField pwdContrasena;
    private PasswordField pwdConfirmar;
    private Button btnRegistrar;
    private Button btnCancelar;
    private Label lblError;

    // Override method to set up the initial stage for testing.
    @Override
    public void start(Stage stage) {
        new Application().start(stage);
        clickOn("#hrefSignUp");
    }
    
    // Test method to verify the initial state of the SignUpController.
    @Test
    public void testA_initialState() {
        // Verifying if the texts are empty
        txtEmail = lookup("#txtEmail").query();
        txtNombreCompleto = lookup("#txtNombreCompleto").query();
        pwdContrasena = lookup("#pwdContrasena").query();
        pwdConfirmar = lookup("#pwdConfirmar").query();
        btnRegistrar = lookup("#btnRegistrar").query();
        btnCancelar = lookup("#btnCancelar").query();
        // Verifying the correct state of the buttons
        verifyThat("#btnCancelar", isEnabled());
        verifyThat("#btnRegistrar", isDisabled());
        // Verifying that the error label is empty
        lblError = lookup("#lblError").query();
    }
   
    // Test method for a simple sign-up scenario.
    @Test
    public void testB_simpleSignUp() {
        // Filling the mandatory fields
        clickOn("#txtEmail");
        write("email"+new Random().nextInt(1000000)+"@gmail.com");
        clickOn("#txtNombreCompleto");
        write("Nombre Apellido");
        clickOn("#pwdContrasena");
        write("Abcd*1234");
        clickOn("#pwdConfirmar");
        write("Abcd*1234");
        // Verifying that the register button is enabled
        verifyThat("#btnRegistrar", isEnabled());
        clickOn("#btnRegistrar");
        // Verifying that the Main Window is visible
        verifyThat("#mainWindow", isVisible());
    }
    
    // Test method for a complete sign-up scenario.
    @Test
    public void testC_fullSignUp() {
        // Filling all the optional fields
        clickOn("#txtEmail");
        write("email"+new Random().nextInt(1000000)+"@gmail.com");
        clickOn("#txtNombreCompleto");
        write("Nombre Apellido");
        clickOn("#pwdContrasena");
        write("Abcd*1234");
        clickOn("#pwdConfirmar");
        write("Abcd*1234");
        clickOn("#txtDireccion");
        write("Dirección");
        clickOn("#txtCodigoPostal");
        write("01234");
        clickOn("#txtTelefonoMovil");
        write("012345678");
        // Verifying that the register button is enabled
        verifyThat("#btnRegistrar", isEnabled());
        clickOn("#btnRegistrar");
        // Verifying that the Main Window is visible
        verifyThat("#mainWindow", isVisible());
    }
 
    // Test method for attempting to sign up with an existing email address.
    @Test
    public void testD_emailAlreadyExists() {
        // Filling the mandatory fields with an existing email
        clickOn("#txtEmail");
        write("email@example.com");
        clickOn("#txtNombreCompleto");
        write("Nombre Apellido");
        clickOn("#pwdContrasena");
        write("Abcd*1234");
        clickOn("#pwdConfirmar");
        write("Abcd*1234");
        // Verifying that the register button is enabled
        verifyThat("#btnRegistrar", isEnabled());
        clickOn("#btnRegistrar");
        // Verifying that the error label shows the EmailAlreadyExists exception error
        verifyThat("#lblError", isVisible());
    }
}
