/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.concurrent.TimeoutException;
import javafx.scene.input.KeyCode;
import main.Application;
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
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * TestFX class for LogInController. Tests aimed to confirm the usability of th
 * window
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class LogInControllerTest extends ApplicationTest {

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

    /**
     * Test of initial state of login view.
     */
    @Test
    public void test1_InitialState() {
        verifyThat("#txtEmail", hasText(""));
        verifyThat("#pwdPassword", hasText(""));
        verifyThat("#loginButton", isDisabled());
    }

    /**
     * Test of the button "Entrar" enable/Disable
     */
    @Test
    public void test2_loginBtnEnabled() {
        clickOn("#txtEmail");
        write("example@mail.com");
        verifyThat("#loginButton", isDisabled());
        eraseText(1);

        clickOn("#pwdPassword");
        write("Abcd*1234");
        verifyThat("#signUpButton", isDisabled());
        eraseText(2);

        clickOn("#txtEmail");
        write("example@mail.com");
        clickOn("#pwdPassword");
        write("Abcd*1234");
        verifyThat("#loginButton", isEnabled());
    }

    /**
     * Test of the format of the email
     */
    public void test3_emailFormat() {
        clickOn("#txtEmail");
        write("correoInvalido");
        clickOn("#pwdPassword"); //Click elsewhere to trigger the validation
        verifyThat("#lblError", isVisible());
        verifyThat("#lblError", hasText("El formato de las credenciales no es correcto"));
    }

    /**
     * Test of the format of the password
     */
    public void test4_passwordFormat() {
        clickOn("#pwdPassword");
        write("password");
        clickOn("#txtEmail");//Click elsewhere to trigger the validation
        verifyThat("#lblError", isVisible());
        verifyThat("#lblError", hasText("El formato de las credenciales no es correcto"));
    }

    /**
     * Test for the hyperlink to the Sign Up Window
     */
    public void test5_hyperlinkSignUp() {
        clickOn("#hrefSignUp");
        verifyThat("#LogInFXML", isInvisible());
        verifyThat("#signUp", isVisible());
    }

    /**
     * Test of the closure of the window
     */
    public void test6_closeWindow() {
        clickOn("#stage");
        type(KeyCode.ALT, KeyCode.F4);
        verifyThat("Confirm Closure", isVisible());
        clickOn("Confirm");
        verifyThat("#stage", isNull());
    }
}
