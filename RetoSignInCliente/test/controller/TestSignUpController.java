package controller;

import java.util.Random;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Application;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;

/**
 * Pruebas de interfaz de usuario utilizando TestFX para SignUpController.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSignUpController extends ApplicationTest {
    private TextField txtEmail;
    private TextField txtNombreCompleto;
    private PasswordField pwdContrasena;
    private PasswordField pwdConfirmar;
    private Text lblError;

    @Override
    public void start(Stage stage) {
        new Application().start(stage);
        clickOn("#hrefSignUp");
    }

    @Test
    public void testA_initialState() {
        // Verificar que los campos de texto estén vacíos
        txtEmail = lookup("#txtEmail").query();
        txtNombreCompleto = lookup("#txtNombreCompleto").query();
        pwdContrasena = lookup("#pwdContrasena").query();
        pwdConfirmar = lookup("#pwdConfirmar").query();
        // Verificar que los botones estén en el estado correcto
        verifyThat("#btnCancelar", isEnabled());
        verifyThat("#btnRegistrar", isDisabled());
        // Verificar que la etiqueta de error esté vacía
        lblError = lookup("#lblError").query();
        verifyThat(lblError, hasText(""));
    }

    @Test
    public void testB_simpleSignUp() {
        // Llenar los campos obligatorios con un correo electrónico único
        clickOn("#txtEmail").write("email" + new Random().nextInt() + "@gmail.com");
        clickOn("#txtNombreCompleto").write("Nombre Apellido");
        clickOn("#pwdContrasena").write("Abcd*1234");
        clickOn("#pwdConfirmar").write("Abcd*1234");
        // Verificar que el botón Registrar esté habilitado
        verifyThat("#btnRegistrar", isEnabled());
        // Realizar clic en el botón Registrar
        clickOn("#btnRegistrar");
        // Verificar que la ventana principal esté visible después del registro
        verifyThat("#mainWindow", isVisible());
    }

    @Test
    public void testC_fullSignUp() {
        // Llenar todos los campos con datos válidos
        clickOn("#txtEmail").write("email" + new Random().nextInt() + "@gmail.com");
        clickOn("#txtNombreCompleto").write("Nombre Apellido");
        clickOn("#pwdContrasena").write("Abcd*1234");
        clickOn("#pwdConfirmar").write("Abcd*1234");
        clickOn("#txtDireccion").write("Dirección");
        clickOn("#txtCodigoPostal").write("01234");
        clickOn("#txtTelefonoMovil").write("012345678");
        // Verificar que el botón Registrar esté habilitado
        verifyThat("#btnRegistrar", isEnabled());
        // Realizar clic en el botón Registrar
        clickOn("#btnRegistrar");
        // Verificar que la ventana principal esté visible después del registro
        verifyThat("#mainWindow", isVisible());
    }

    @Test
    public void testD_emailAlreadyExists() {
        // Llenar los campos con un correo electrónico que ya existe
        clickOn("#txtEmail").write("email@example.com");
        clickOn("#txtNombreCompleto").write("Nombre Apellido");
        clickOn("#pwdContrasena").write("Abcd*1234");
        clickOn("#pwdConfirmar").write("Abcd*1234");
        // Verificar que el botón Registrar esté habilitado
        verifyThat("#btnRegistrar", isEnabled());
        // Realizar clic en el botón Registrar
        clickOn("#btnRegistrar");
        // Verificar que se muestre el mensaje de error adecuado
        verifyThat(lblError, hasText("Este email ya existe."));
    }
}
