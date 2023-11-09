/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import main.Application;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author 2dam
 */
public class TestMainWindow extends ApplicationTest {

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

    @Test
    public void test1_MainWindow() {
        clickOn("#txtEmail");
        write("test@check.com");
        clickOn("#pwdPassword");
        write("abcd*1234");
        clickOn("#loginButton");
        FxAssert.verifyThat("#mainWindow", isVisible());
    }

    @Test
    public void test2_MainWindow() {
        clickOn("#btnLogout");
        FxAssert.verifyThat("#login", isVisible());
    }

    @Test
    public void test3_MainWindow() {
        clickOn("#txtEmail");
        write("test@check.com");
        clickOn("#pwdPassword");
        write("abcd*1234");
        clickOn("#loginButton");
        FxAssert.verifyThat("#mainWindow", isVisible());
    }

    @Test
    public void test4_MainWindow() {
        closeCurrentWindow();
    }
}
