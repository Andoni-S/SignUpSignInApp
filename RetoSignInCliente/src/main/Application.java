package main;

import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import controller.LogInController;
import logic.SignableImplementation;

/**
 *
 * The main class for launching the JavaFX application. Extends the
 * {@link javafx.application.Application} class and provides the entry point for
 * the application.
 * <p>
 * The {@code start} method initializes the primary stage (main window) and sets
 * up the user interface by loading the Log In window using the
 * {@link controller.LogInController} class.
 * </p>
 * <p>
 * The {@code main} method serves as the entry point for the application,
 * initiating the launch of the JavaFX application.
 * </p>
 *
 * @author Jagoba Bartolomé
 * @author Ander Goirigolzarri Iturburu
 * @author Andoni Sanz
 */
public class Application extends javafx.application.Application {

    private final static java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(SignableImplementation.class.getName());

    /**
     * This method is called when the JavaFX application is launched. It is used
     * to initialize the primary stage (the main window) and set up the user
     * interface of the application.
     *
     * @param primaryStage The primary stage for this application, where the
     * application scene can be set. The first stage represents the Log In
     * Window of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInFXML.fxml"));
            Parent root = loader.load();
            LogInController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.initStage(root);
            primaryStage.show();
            LOGGER.info("Log in window set.");
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * The main method serving as the entry point for the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.info("Application initiated.");
        launch(args);
    }
}
