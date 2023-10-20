package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import libraries.ApplicationPDU;
import libraries.MessageType;
import libraries.User;

/**
 *
 * @author 2dam
 */
public class Client{
   
    private final int PUERTO = 5004;
    private final String IP = "192.168.21.0";
    private Socket sCliente = null;
    
    /**
     * This method connects the Client with the Server through a Socket, 
     * if the Client reads a message, it's warning that there are no connections left
     * @throws ClassNotFoundException, IOException
     */   
    public void iniciar() throws ClassNotFoundException, IOException{
        ObjectInputStream entrada = null;
        ObjectOutputStream salida = null;

        sCliente = new Socket(IP, PUERTO);
        entrada = new ObjectInputStream(sCliente.getInputStream());
        salida = new ObjectOutputStream(sCliente.getOutputStream());

        String noConnection = (String) entrada.readObject();
        System.out.println(noConnection);

    }
/**
     * This is the main method
     * @param args
     * @throws ClassNotFoundException
     * @throws IOException 
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Client c1 = new Client();
        c1.iniciar();
        launch(args);
    }
    
    /**
     * This method is called when the JavaFX application is launched. It is used
     * to initialize the primary stage (the main window) and set up the user
     * interface of the application.
     *
     * @param primaryStage The primary stage for this application, where
     * the application scene can be set. The stage represents the main window of
     * the application.
     */
    public void start(Stage primaryStage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInFXML.fxml"));
        Parent root = loader.load();
        LogInController controller = loader.getController();
        controller.initStage(primaryStage); // Pass the Stage to the controller
        primaryStage.setTitle("Log in");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    
    /**
     * This method writes a User through the Socket with the MessageType indicating that its a login
     * @param User u
     * @throws IOException
     */
    public void checkUser(User u) throws IOException{
        ObjectOutputStream salida = null;
        ApplicationPDU app = null;
        
        app.setMessageType(MessageType.LogIn);
        app.setUser(u);
        salida = new ObjectOutputStream(sCliente.getOutputStream());
        salida.writeObject(app);
    }
    
    /**
     * This method writes a User through the Socket with the MessageType indicating that its a register
     * @param User u
     * @throws IOException
     */
    public void sendUser(User u) throws IOException{
        ObjectOutputStream salida = null;
        ApplicationPDU app = null;
        
        app.setMessageType(MessageType.SignIn);
        app.setUser(u);
        salida = new ObjectOutputStream(sCliente.getOutputStream());
        salida.writeObject(app);
    }
    
    /**
     * This method reads the data of a User from the Server and converts it into an object User
     * @return User
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public User getUser() throws IOException, ClassNotFoundException{
        ObjectInputStream entrada = null;
        
        ApplicationPDU app = (ApplicationPDU) entrada.readObject();
        User u = app.getUser();
        return u;
    }
    
    
}