package controller;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import main.Application;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * TestFX class for SignUpController. UI tests using TestFX for
 * SignUpController. Tests cover scenarios such as the initial state of
 * SignUpController, a simple sign-up scenario, a complete sign-up scenario, and
 * attempting to sign up with an existing email address.
 *
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

    private static String mail;

    /**
     * Sets up the TestFX environment for the entire test class. This method is
     * annotated with {@code @BeforeClass}, indicating that it runs once before
     * any tests in the class. It registers the primary stage and sets up the
     * application using TestFX.
     *
     * @throws TimeoutException If a timeout occurs during the setup.
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);
    }

    /**
     * Test method to verify the initial state of the SignUpController.
     *
     * This method checks if the required fields are empty, verifies the correct
     * state of buttons, and ensures that the error label is empty.
     */
    @Test
    public void testA_initialState() {
        //Changing windows
        clickOn("#hrefSignUp");
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

    /**
     * Test method for a simple sign-up scenario.
     *
     * This method simulates a simple sign-up scenario by filling in mandatory
     * fields, checking if the register button is enabled, and verifying that
     * the Main Window is visible after registration.
     */
    @Test
    public void testB_simpleSignUp() {
        // Filling the mandatory fields
        clickOn("#txtEmail");
        write("email" + new Random().nextInt(1000000) + "@gmail.com");
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
        //Changing windows
        clickOn("#btnLogout");
    }

    /**
     * Test method for a complete sign-up scenario.
     *
     * This method simulates a complete sign-up scenario by filling in all
     * fields, checking if the register button is enabled, and verifying that
     * the Main Window is visible after registration.
     */
    @Test
    public void testC_fullSignUp() {
        // Filling all the optional fields
        clickOn("#hrefSignUp");
        clickOn("#txtEmail");
        mail = "email" + new Random().nextInt(1000000) + "@gmail.com";
        write(mail);
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
        clickOn("#btnLogout");
    
    }

    /**
     * Test method for attempting to sign up with an existing email address.
     *
     * This method simulates attempting to sign up with an existing email
     * address, checking if the register button is enabled, and verifying that
     * the error label displays the EmailAlreadyExists exception error.
     */
    @Test
    public void testD_emailAlreadyExists() {
        // Filling the mandatory fields with an existing email
        clickOn("#hrefSignUp");
        clickOn("#txtEmail");
        write(mail);
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
