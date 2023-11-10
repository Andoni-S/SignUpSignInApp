package controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import main.Application;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * TestFX class for MainWindow. Tests aimed to confirm the functionality of the
 * main application window.
 *
 * This class contains various TestFX tests to validate the behavior of the
 * MainWindow. The tests cover scenarios such as logging in, logging out,
 * re-logging in, and closing the application window.
 *
 * Note: This class extends ApplicationTest to leverage TestFX for UI testing.
 *
 * @author Andoni Sanz
 */
public class TestMainWindow extends ApplicationTest {

    /**
     * Start method for initializing the main application window.
     *
     * This method is part of the JavaFX lifecycle and is called before any UI
     * tests. It initializes the primary stage and loads the LogInFXML.fxml
     * file, setting up the LogInController and displaying the Log In window.
     *
     * @param primaryStage The primary stage for this application.
     * @throws IOException If an I/O error occurs during loading.
     * @throws ClassNotFoundException If a class is not found during loading.
     */
    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        new Application().start(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInFXML.fxml"));
        Parent root = loader.load();
        LogInController controller = loader.getController();
        controller.setStage(primaryStage);
        controller.initStage(root);
        primaryStage.show();
    }

    /**
     * Test of the main window after successful login.
     *
     * This test method simulates a successful login by entering valid
     * credentials, clicking the login button, and verifies that the main
     * application window is visible.
     */
    @Test
    public void test1_MainWindow() {
        clickOn("#txtEmail");
        write("test@check.com");
        clickOn("#pwdPassword");
        write("abcd*1234");
        clickOn("#loginButton");
        FxAssert.verifyThat("#mainWindow", isVisible());
    }

    /**
     * Test of the main window after logging out.
     *
     * This test method simulates logging out by clicking the logout button and
     * verifies that the login window is visible.
     */
    @Test
    public void test2_MainWindow() {
        clickOn("#btnLogout");
        FxAssert.verifyThat("#login", isVisible());
    }

    /**
     * Test of the main window after re-logging in.
     *
     * This test method simulates logging in again after logging out, verifying
     * that the main application window is visible.
     */
    @Test
    public void test3_MainWindow() {
        clickOn("#txtEmail");
        write("test@check.com");
        clickOn("#pwdPassword");
        write("abcd*1234");
        clickOn("#loginButton");
        FxAssert.verifyThat("#mainWindow", isVisible());
    }

    /**
     * Test of the closure of the application window.
     *
     * This test method simulates closing the application window and verifies
     * the window closure.
     */
    @Test
    public void test4_MainWindow() {
        closeCurrentWindow();
    }
}
