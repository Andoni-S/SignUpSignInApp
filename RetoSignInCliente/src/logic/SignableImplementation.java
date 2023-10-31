package logic;

import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    private final int PUERTO = 6666;
    //TODO 
    /**
     * The IP address for the socket communication.
     */
    private final String IP = "127.0.0.1";
    /**
     * The client socket.
     */
    private Socket sCliente = null;
    
    /**
     * This method writes a User through the Socket with the MessageType indicating that its a login. 
     * It returns a User with all the necessary data and a MessageType indicating any exception.
     * @param u
     * @throws IOException, ClassNotFoundException, CredentialsException, EmailAlreadyExistsException
     * @throws exceptions.EmailAlreadyExistsException
     */
    @Override
    public User logIn(User u) throws IOException, ClassNotFoundException, CredentialsException, EmailAlreadyExistsException, ServerErrorException{
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        sCliente = new Socket(IP, PUERTO);
        entrada = new ObjectInputStream(sCliente.getInputStream());
        salida = new ObjectOutputStream(sCliente.getOutputStream());

        String noConnection = (String) entrada.readObject();
        System.out.println(noConnection);
        
        ApplicationPDU pdu = new ApplicationPDU();
        
        pdu.setMessageType(MessageType.LogIn);
        pdu.setUser(u);
        salida.writeObject(pdu); 
        pdu = (ApplicationPDU) entrada.readObject();
        
        if (pdu.getMessageType().toString().equals("Ex_Credentials")){
            throw new CredentialsException();
        }
        if (pdu.getMessageType().toString().equals("Ex_EmailAlreadyExists")){
            throw new EmailAlreadyExistsException();
        }
        if (pdu.getMessageType().toString().equals("Ex_ServerError")){
            throw new ServerErrorException();
        }
        return pdu.getUser();      
    }
    
    /**
     * This method writes a User through the Socket with the MessageType indicating that its a register.
     * It returns a User with all the necessary data and a MessageType indicating any exception.
     * @param User u
     * @throws IOException
     */
    public User signUp(User u) throws IOException, ClassNotFoundException, EmailAlreadyExistsException, ServerErrorException{
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        sCliente = new Socket(IP, PUERTO);
        entrada = new ObjectInputStream(sCliente.getInputStream());
        salida = new ObjectOutputStream(sCliente.getOutputStream());

        String connectionACK = (String) entrada.readObject();
        System.out.println(connectionACK);
        
        ApplicationPDU pdu = new ApplicationPDU();
        
        pdu.setMessageType(MessageType.SignUp);
        pdu.setUser(u);
        salida.writeObject(pdu);
        
        entrada = new ObjectInputStream(sCliente.getInputStream());
        pdu = (ApplicationPDU) entrada.readObject();
        if (pdu.getMessageType().toString().equals("Ex_EmailAlreadyExists")){
            throw new EmailAlreadyExistsException();
        }
        if (pdu.getMessageType().toString().equals("Ex_ServerError")){
            throw new ServerErrorException();
        }
        return pdu.getUser();
    }
}
