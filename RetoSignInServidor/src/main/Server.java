package main;

import Threads.CloseThread;
import Threads.DeclineThread;
import Threads.WorkerThread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import libraries.User;
import connection.Pool;
import java.util.logging.Logger;

/**
 *
 * The Server class manages the configuration settings for a server application.
 *
 * @author Andoni Sanz
 */
public class Server {

    /**
     * ResourceBundle for loading configuration properties from the
     * "properties.Config" file.
     */
    private ResourceBundle configFile = ResourceBundle.getBundle("properties.Config");
    /**
     * The port number on which the server listens for incoming connections.
     */
    private final int PUERTO = 6666;
    /**
     * The current number of connected clients.
     */
    private static int clienteN = 0;
    /**
     * The maximum number of allowed clients as specified in the configuration.
     */
    private final int MAXIMUM_CLIENTS = Integer.valueOf(configFile.getString("MAXCLIENTS"));
    /**
     * A flag indicating whether the server is set to close (true) or remain
     * open (false).
     */
    private static boolean close = false;

    /**
     * Initializes the server, starts a CloseThread, and sets up a connection
     * pool. Listens for incoming client connections and handles them
     * accordingly.
     */
    public void iniciar() {

        ServerSocket servidor = null;
        Socket cliente = null;

        try {
            CloseThread ct = new CloseThread();
            ct.start();

            Pool pool = Pool.getPool();

            //Instance ServerSocket
            servidor = new ServerSocket(PUERTO);
            Logger.getLogger(WorkerThread.class.getName()).info("MAXIMUM CLIENTS: " + MAXIMUM_CLIENTS);

            //cicle of admission of new clients
            while (!close) {

                User user = new User();
                //aceptar un cliente en el servidor
                Logger.getLogger(WorkerThread.class.getName()).info("Waiting for connections from the client...");
                cliente = servidor.accept();
                Logger.getLogger(WorkerThread.class.getName()).info("Client " + clienteN + " connected");

                //If the number of clients has not reached the maximum
                if (clienteN < MAXIMUM_CLIENTS) {
                    //create a worker thread where SignIn/SignUp operations are done

                    WorkerThread wt = new WorkerThread(cliente);
                    incrementClienteN();

                    wt.start();

                } else { //In case you have reached the maximum number of clients create a thread to reject the client.
                    DeclineThread dt = new DeclineThread(cliente);
                    dt.start();
                }
                Logger.getLogger(WorkerThread.class.getName()).info("Quantity of the clients: " + clienteN);
            }
        } catch (IOException e) {
            Logger.getLogger(WorkerThread.class.getName()).severe("Error: " + e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(WorkerThread.class.getName()).severe("Error: " + e.getMessage());
        } finally {
            try {
                if (servidor != null) {
                    servidor.close();
                }
                if (cliente != null) {
                    cliente.close();
                }
                Logger.getLogger(WorkerThread.class.getName()).info("Server closed.");
            } catch (IOException e) {
                Logger.getLogger(WorkerThread.class.getName()).severe("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Increments the count of connected clients in a synchronized manner.
     *
     * @return The updated count of connected clients.
     */
    public synchronized static int incrementClienteN() {
        return clienteN++;
    }

    /**
     * Decrements the count of connected clients in a synchronized manner.
     *
     * @return
     */
    public synchronized static int decrementClienteN() {
        return clienteN--;
    }

    /**
     * Gets the current count of connected clients in a synchronized manner.
     *
     * @return The count of connected clients.
     */
    public synchronized static int getClienteN() {
        return clienteN;
    }

    /**
     * Sets the count of connected clients in a synchronized manner.
     *
     * @param clienteN The new count of connected clients.
     */
    public synchronized static void setClienteN(int clienteN) {
        Server.clienteN = clienteN;
    }

    /**
     * Sets the flag indicating whether the server is set to close or remain
     * open in a synchronized manner.
     *
     * @param close The new value of the close flag.
     */
    public synchronized static void setClose(boolean close) {
        Server.close = close;
    }

    /**
     * Initializes the server
     *
     * @param args
     */
    public static void main(String[] args) {
        Server s1 = new Server();
        s1.iniciar();
    }
}
