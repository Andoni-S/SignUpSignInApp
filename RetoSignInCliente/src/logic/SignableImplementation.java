package logic;

import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import libraries.ApplicationPDU;
import libraries.MessageType;
import libraries.Signable;
import libraries.User;

/**
 * Implementation of the {@link Signable} interface for handling user
 * authentication and registration using socket communication with a server.
 * <p>
 * This class establishes a connection with the server and performs user login
 * and registration operations by sending and receiving data through a
 * {@link Socket}. It handles exceptions such as {@link CredentialsException},
 * {@link EmailAlreadyExistsException}, and {@link ServerErrorException}.
 * </p>
 * <p>
 * The server's IP address and port number are retrieved from a configuration
 * file, and the communication is facilitated through the {@link ApplicationPDU}
 * class for data exchange.
 * </p>
 *
 * @author Jagoba Bartolomé Barroso
 */
public class SignableImplementation implements Signable {

    /**
     * The IP address for the socket communication.
     */
    private ResourceBundle configFile = ResourceBundle.getBundle("properties.Config");
    private final String IP = configFile.getString("IP");
    /**
     * The port number for the socket communication.
     */
    private final int PUERTO = Integer.valueOf(configFile.getString("PORT_C"));
    /**
     * The client socket.
     */
    private Socket sCliente = null;
    private final static java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(SignableImplementation.class.getName());

    /**
     * This method writes a User through the Socket with the MessageType
     * indicating that it's a login. It returns a User with all the necessary
     * data and a MessageType indicating any exception.
     *
     * @param u The user attempting to log in.
     * @return A User object with authenticated data.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if the class of a serialized object cannot
     * be found.
     * @throws CredentialsException if the provided credentials are invalid.
     * @throws EmailAlreadyExistsException if the email already exists during
     * registration.
     * @throws ServerErrorException if a server error occurs during the
     * operation.
     */
    @Override
    public User logIn(User u) throws IOException, ClassNotFoundException, CredentialsException, EmailAlreadyExistsException, ServerErrorException {
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        sCliente = new Socket(IP, PUERTO);
        entrada = new ObjectInputStream(sCliente.getInputStream());
        salida = new ObjectOutputStream(sCliente.getOutputStream());
        LOGGER.info("Opening input and output streams.");
        ApplicationPDU pdu = new ApplicationPDU();
        pdu = (ApplicationPDU) entrada.readObject();
        if (pdu.getMessageType().toString().equals("Accept")) {
            LOGGER.info("Connection with the server.");
            pdu.setMessageType(MessageType.LogIn);
            pdu.setUser(u);
            LOGGER.info("Connection with the server.");
            salida.writeObject(pdu);
            pdu = (ApplicationPDU) entrada.readObject();
            if (pdu.getMessageType().toString().equals("Ex_Credentials")) {
                LOGGER.info("Credential error.");
                throw new CredentialsException("Error en las credenciales");
            }
            if (pdu.getMessageType().toString().equals("Ex_ServerError")) {
                LOGGER.info("Server error.");
                throw new ServerErrorException();
            }
        } else if (pdu.getMessageType().toString().equals("Ex_ServerError")) {
            LOGGER.info("Server error.");
            throw new ServerErrorException();
        }
        return pdu.getUser();
    }

    /**
     * This method writes a User through the Socket with the MessageType
     * indicating that it's a register. It returns a User with all the necessary
     * data and a MessageType indicating any exception.
     *
     * @param u The user to be registered.
     * @return A User object with registered data.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if the class of a serialized object cannot
     * be found.
     * @throws EmailAlreadyExistsException if the email already exists during
     * registration.
     * @throws ServerErrorException if a server error occurs during the
     * operation.
     */
    public User signUp(User u) throws IOException, ClassNotFoundException, EmailAlreadyExistsException, ServerErrorException {
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        sCliente = new Socket(IP, PUERTO);
        entrada = new ObjectInputStream(sCliente.getInputStream());
        salida = new ObjectOutputStream(sCliente.getOutputStream());
        LOGGER.info("Opening input and output streams.");
        ApplicationPDU pdu = new ApplicationPDU();
        pdu = (ApplicationPDU) entrada.readObject();
        if (pdu.getMessageType().toString().equals("Accept")) {
            LOGGER.info("Connection with the server.");
            pdu.setMessageType(MessageType.SignUp);
            pdu.setUser(u);
            LOGGER.info("User saved in the PDU.");
            salida.writeObject(pdu);
            pdu = (ApplicationPDU) entrada.readObject();
            if (pdu.getMessageType().toString().equals("Ex_EmailAlreadyExists")) {
                LOGGER.info("Existing email.");
                throw new EmailAlreadyExistsException();
            }
            if (pdu.getMessageType().toString().equals("Ex_ServerError")) {
                LOGGER.info("Server error.");
                throw new ServerErrorException();
            }
        } else if (pdu.getMessageType().toString().equals("Ex_ServerError")) {
            LOGGER.info("Server error.");
            throw new ServerErrorException();
        }
        return pdu.getUser();
    }
}
