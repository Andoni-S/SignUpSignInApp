package controller;

import java.util.Random;
import java.util.concurrent.TimeoutException;
import main.Application;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 * TestFX class for LogInController. Tests aimed to confirm the usability of the
 * window.
 *
 * This class contains various TestFX tests to validate the functionality and
 * behavior of the LogInController. The tests cover scenarios such as the
 * initial state of the login view, enabling/disabling the "Entrar" button,
 * email and password format checks, navigation to the Sign Up window,
 * successful login, and the closure of the window.
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class LogInControllerTest extends ApplicationTest {

    /**
     * Set up the Window for testing.
     *
     * This method is annotated with `@BeforeClass` to perform setup tasks
     * before the class is initiated. It registers the primary stage and sets up
     * the JavaFX application for testing.
     *
     * @throws TimeoutException If a timeout occurs during setup.
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);
    }

    /**
     * Tear down the test environment.
     *
     * This method is called after each test method to clean up the test
     * environment. It erases the content from the email and password fields.
     */
    public void tearDown() {
        doubleClickOn("#txtEmail").eraseText(25); // Deletes the content from the email field
        doubleClickOn("#pwdPassword").eraseText(25); // Deletes the content from the password field
    }

    /**
     * Test of initial state of login view.
     *
     * This test method checks the initial state of the login view, verifying
     * that the email and password fields are empty, and the "Entrar" button is
     * disabled.
     */
    @Test
    public void test1_InitialState() {
        verifyThat("#txtEmail", hasText(""));
        verifyThat("#pwdPassword", hasText(""));
        verifyThat("#loginButton", isDisabled());
        this.tearDown();
    }

    /**
     * Test of the button "Entrar" enable/Disable.
     *
     * This test method checks the enabling and disabling of the "Entrar" button
     * based on the content of the email and password fields.
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
     * Test for the email format.
     *
     * This test method checks the handling of the email format, displaying an
     * error message if the email is in an invalid format.
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
     * Test for the password format.
     *
     * This test method checks the handling of the password format, displaying
     * an error message if the password is in an invalid format.
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
     * Test for the hyperlink to the Sign Up Window.
     *
     * This test method checks the navigation to the Sign Up window when the
     * hyperlink is clicked.
     */
    @Test
    public void test5_hyperlinkSignUp() {
        clickOn("#hrefSignUp");
        verifyThat("#signUp", isVisible());
        clickOn("#btnCancelar");
        clickOn("Aceptar");
        this.tearDown();
    }

    /**
     * Login test.
     *
     * This test method performs a comprehensive test of the login process,
     * including filling out the registration form, successful registration, and
     * subsequent login.
     */
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
     * Test of the closure of the window.
     *
     * This test method checks the closure of the window and the display of a
     * confirmation dialog.
     */
    @Test
    public void test7_closeWindow() {
        closeCurrentWindow();
        verifyThat("Are you sure you want to close the window?", isVisible());
        clickOn("Confirm");
    }
}
