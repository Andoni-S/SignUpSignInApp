package Threads;

import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;
import factory.SignableFactory;
import libraries.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import libraries.ApplicationPDU;
import libraries.MessageType;
import libraries.Signable;
import main.Server;

/**
 * WorkerThread represents a thread that handles client requests in a server application.
 */
public class WorkerThread extends Thread {

    Socket socket = null;
    ObjectInputStream entrada = null;
    ObjectOutputStream salida = null;
    User user = null;
    ApplicationPDU pdu = null;

    /**
     * Constructs a new WorkerThread with a given socket.
     *
     * @param socket The socket for communication with the client.
     */
    public WorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Launching thread");
        try {
            // Initialize input and output streams
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            // Send a welcome message to the client
            salida.writeObject("You have entered the server");

            // Read a request PDU from the client
            pdu = (ApplicationPDU) entrada.readObject();

            Signable s = SignableFactory.getSignable();

            if (pdu.getMessageType().toString().equalsIgnoreCase("LogIn")) {
                System.out.println("Verifying user in the database");
                user = new User();
                user = s.logIn(pdu.getUser());
                pdu.setMessageType(MessageType.LogIn);
                pdu.setUser(user);
                salida.writeObject(pdu);
                interrupt();

            } else if (pdu.getMessageType().toString().equalsIgnoreCase("SignUp")) {
                System.out.println("Registering user in the database");
                user = new User();
                user = s.signUp(pdu.getUser());
                pdu.setMessageType(MessageType.SignUp);
                pdu.setUser(user);
                salida.writeObject(pdu);
                interrupt();
            } else {
                // This should not occur; it should always be Sign In or Sign Up
                System.out.println("Some type of error occurred");
            }
        } catch (IOException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            pdu = new ApplicationPDU();
            pdu.setMessageType(MessageType.Ex_ClassNotFound);
            interrupt();
        } catch (ServerErrorException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            pdu = new ApplicationPDU();
            pdu.setMessageType(MessageType.Ex_ServerError);
            interrupt();
        } catch (CredentialsException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            pdu = new ApplicationPDU();
            pdu.setMessageType(MessageType.Ex_Credentials);
            interrupt();
        } catch (EmailAlreadyExistsException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            pdu = new ApplicationPDU();
            pdu.setMessageType(MessageType.Ex_EmailAlreadyExists);
            interrupt();
        } finally {
            try {
                salida.writeObject(pdu);
                Server.setClienteN(Server.getClienteN()-1);
            } catch (IOException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
