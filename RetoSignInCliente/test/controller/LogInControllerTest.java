package controller;

import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import main.Application;
import org.junit.After;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isNull;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 * TestFX class for LogInController. Tests aimed to confirm the usability of th
 * window
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class LogInControllerTest extends ApplicationTest {

    private TextField txtEmail;
    private PasswordField pwdPassword;

    /**
     * Set up the Window for testing
     *
     * @throws TimeoutException
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);
    }

    public void tearDown() {
        doubleClickOn("#txtEmail").eraseText(25); // Deletes the content from the email field
        doubleClickOn("#pwdPassword").eraseText(25); // Deletes the content from the password field
    }

    /**
     * Test of initial state of login view.
     */
    @Test
    public void test1_InitialState() {
        verifyThat("#txtEmail", hasText(""));
        verifyThat("#pwdPassword", hasText(""));
        verifyThat("#loginButton", isDisabled());
        this.tearDown();
    }

    /**
     * Test of the button "Entrar" enable/Disable
     */
    @Test
    public void test2_loginBtnEnabled() {
        clickOn("#txtEmail");
        write("example@mail.com");
        verifyThat("#loginButton", isDisabled());
        doubleClickOn("#txtEmail");
        eraseText(2);
        doubleClickOn("#txtEmail");
        eraseText(1);

        clickOn("#pwdPassword");
        write("Abcd*1234");
        verifyThat("#loginButton", isDisabled());
        doubleClickOn("#pwdPassword");
        eraseText(2);

        clickOn("#txtEmail");
        write("example@mail.com");
        clickOn("#pwdPassword");
        write("Abcd*1234");
        verifyThat("#loginButton", isEnabled());
        this.tearDown();
    }

    /**
     * Test for the email format
     */
    @Test
    public void test3_emailFormat() {
        clickOn("#txtEmail");
        write("invalidMail");
        clickOn("#pwdPassword");
        write("Abcd*1234");
        clickOn("#loginButton");
        verifyThat("#lblError", isVisible());
        this.tearDown();
    }

    /**
     * Test for the password format
     */
    @Test
    public void test4_passwordFormat() {
        clickOn("#pwdPassword");
        write("password");
        clickOn("#txtEmail");
        write("example@mail.com");
        clickOn("#loginButton");
        verifyThat("#lblError", isVisible());
        this.tearDown();
    }

    /**
     * Test for the hyperlink to the Sign Up Window
     */
    @Test
    public void test5_hyperlinkSignUp() {
        clickOn("#hrefSignUp");
        verifyThat("#signUp", isVisible());
        clickOn("#btnCancelar");
        clickOn("Aceptar");
        this.tearDown();
    }

    @Test
    public void test6_loginTest() {
        clickOn("#hrefSignUp");
        verifyThat("#signUp", isVisible());
        clickOn("#txtEmail");
        String emailtest = "email" + new Random().nextInt(1000000) + "@gmail.com";
        write(emailtest);
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
        verifyThat("#btnRegistrar", isEnabled());
        clickOn("#btnRegistrar");
        verifyThat("#mainWindow", isVisible());
        clickOn("#btnLogout");
        clickOn("#txtEmail");
        write(emailtest);
        clickOn("#pwdPassword");
        write("Abcd*1234");
        clickOn("#loginButton");
        verifyThat("#textPostalCode", isVisible());
        clickOn("#btnLogout");
    }

    /**
     * Test of the closure of the window
     */
    @Test
    public void test7_closeWindow() {
        closeCurrentWindow();
        verifyThat("Are you sure you want to close the window?", isVisible());
        clickOn("Confirm");
    }
}
