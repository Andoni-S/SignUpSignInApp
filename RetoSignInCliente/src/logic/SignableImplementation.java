package logic;

import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;
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
import libraries.Signable;
import libraries.User;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class SignableImplementation implements Signable{
   /**
    * The port number for the socket communication.
    */
    private final int PUERTO = 5004;
    //TODO 
    /**
     * The IP address for the socket communication.
     */
    private final String IP = "192.168.21.0";
    /**
     * The client socket.
     */
    private Socket sCliente = null;
    
    /**
     * This method writes a User through the Socket with the MessageType indicating that its a login. 
     * It returns a User with all the necessary data and a MessageType indicating any exception.
     * @param u
     * @throws IOException, ClassNotFoundException, CredentialsException, EmailAlreadyExistsException
     */
    @Override
    public User logIn(User u) throws CredentialsException, ServerErrorException{
        try {
            ObjectOutputStream salida = null;
            ObjectInputStream entrada = null;
            sCliente = new Socket(IP, PUERTO);
            entrada = new ObjectInputStream(sCliente.getInputStream());
            salida = new ObjectOutputStream(sCliente.getOutputStream());
            
            String noConnection = (String) entrada.readObject();
            System.out.println(noConnection);
            
            ApplicationPDU pdu = null;
            
            pdu.setMessageType(MessageType.LogIn);
            pdu.setUser(u);
            salida = new ObjectOutputStream(sCliente.getOutputStream());
            salida.writeObject(pdu);
            
            entrada = new ObjectInputStream(sCliente.getInputStream());
            pdu = (ApplicationPDU) entrada.readObject();
            if (pdu.getMessageType().toString().equals("Ex_Credentials")){
                throw new CredentialsException();
            }
            if (pdu.getMessageType().toString().equals("Ex_ServerError")){
                throw new ServerErrorException();
            }
            u = pdu.getUser();
            return pdu.getUser();
        } catch (IOException ex) {
            Logger.getLogger(SignableImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignableImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
    
    /**
     * This method writes a User through the Socket with the MessageType indicating that its a register.
     * It returns a User with all the necessary data and a MessageType indicating any exception.
     * @param User u
     * @throws IOException
     */
    @Override
    public User signUp(User u) throws EmailAlreadyExistsException, ServerErrorException{
        try {
            ObjectOutputStream salida = null;
            ObjectInputStream entrada = null;
            sCliente = new Socket(IP, PUERTO);
            entrada = new ObjectInputStream(sCliente.getInputStream());
            salida = new ObjectOutputStream(sCliente.getOutputStream());
            
            String noConnection = (String) entrada.readObject();
            System.out.println(noConnection);
            
            ApplicationPDU pdu = null;
            
            pdu.setMessageType(MessageType.SignUp);
            pdu.setUser(u);
            salida = new ObjectOutputStream(sCliente.getOutputStream());
            salida.writeObject(pdu);
            
            entrada = new ObjectInputStream(sCliente.getInputStream());
            pdu = (ApplicationPDU) entrada.readObject();
            if (pdu.getMessageType().toString().equals("Ex_EmailAlreadyExists")){
                throw new EmailAlreadyExistsException("This email already exists.");
            }
            if (pdu.getMessageType().toString().equals("Ex_ServerError")){
                throw new ServerErrorException("An error with the server has occurred.");
            }     
            u = pdu.getUser();      
        } catch (IOException ex) {
            Logger.getLogger(SignableImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignableImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
}
