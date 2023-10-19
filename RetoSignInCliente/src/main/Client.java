package main;

import libraries.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class Client extends Application {

    /**
     * @param args the command line arguments
     */
    private final int PUERTO = 5004;
    private final String IP = "192.168.21.32";

    public void iniciar() throws ClassNotFoundException {
        Socket sCliente = null;
        ObjectInputStream entrada = null;
        ObjectOutputStream salida = null;

        try {
            sCliente = new Socket(IP, PUERTO);
            entrada = new ObjectInputStream(sCliente.getInputStream());
            salida = new ObjectOutputStream(sCliente.getOutputStream());

            String noConnection = (String) entrada.readObject();
            System.out.println(noConnection);
            //salida.writeObject();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @return
     */
    public User sendUserToDB() {
        //User y MessageType
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException {

        Client c1 = new Client();
        c1.iniciar();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("your_fxml_file.fxml"));
        Parent root = loader.load();
        //MyController controller = loader.getController();
        //controller.initStage(primaryStage); // Pass the Stage to the controller
        primaryStage.setTitle("Your Application");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
