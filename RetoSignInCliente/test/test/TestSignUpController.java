/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Application;
import org.junit.FixMethodOrder;
import org.testfx.framework.junit.ApplicationTest;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSignUpController extends ApplicationTest {
    private TextField txtEmail;
    private TextField txtNombreCompleto;
    private PasswordField pwdContrasena;
    private PasswordField pwdConfirmar;
    private TextField txtDireccion;
    private TextField txtCodigoPostal;
    private TextField txtTelefonoMovil;
    private Button btnCancelar;
    private Button btnConfirmar;
    private Label lblError;
    
    @Override
    public void start(Stage stage){
        //Start JavaFX Application
        new Application().start(stage);     
        //Change windows from Log In to Sign Up
        clickOn("#hrefSignUp");
    }
    /**
     * Test of the initial state of the SignUp window, making sure all the textfields and buttons have the correct state
     */
    @Test
    public void testA_initialState() {
        //Lookup of all the fields
        txtEmail = lookup("#txtEmail").query();
        txtNombreCompleto = lookup("#txtNombreCompleto").query();
        pwdContrasena = lookup("#pwdContrasena").query();
        pwdConfirmar = lookup("#pwdConfirmar").query();
        txtDireccion = lookup("#txtDireccion").query();
        txtCodigoPostal = lookup("#txtCodigoPostal").query();
        txtTelefonoMovil = lookup("#txtTelefonoMovil").query();
        //Verify the state of the buttons
        verifyThat("#btnCancelar", isEnabled());
        verifyThat("#btnRegistrar", isDisabled());
    }
    /**
     * Simple sign up test with the required fields complete
     */
    @Test
    public void testB_simpleSignUp() {
        clickOn("#txtEmail");
        write("email@mail.com");
        clickOn("#txtNombreCompleto");
        write("Nombre apellido");
        clickOn("#pwdContrasena");
        write("Abcd*1234");
        clickOn("#pwdConfirmar");
        write("Abcd*1234");
        clickOn("#btnRegistrar");
        verifyThat("#mainWindow", isVisible());
    }
    
    @Test
    public void testC_fullSignUp(){
        clickOn("#txtEmail");
        write("email@mail.com");
        clickOn("#txtNombreCompleto");
        write("Nombre apellido");
        clickOn("#pwdContrasena");
        write("Abcd*1234");
        clickOn("#pwdConfirmar");
        write("Abcd*1234");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtCodigoPostal");
        write("01234");
        clickOn("#txtTelefonoMovil");
        write("012345678");
        clickOn("#btnRegistrar");
        verifyThat("#mainWindow", isVisible());
    }
}
