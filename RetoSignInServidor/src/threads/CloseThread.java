package Threads;

import connection.Pool;
import static java.lang.System.exit;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Server;

/**
 * The CloseThread class extends Thread and is responsible for handling the
 * server shutdown process. It listens for a specific input key to be entered,
 * triggering the server closure.
 *
 * @author Andoni Sanz
 */
public class CloseThread extends Thread {
/**
     * ResourceBundle for loading configuration properties from the "properties.Config" file.
     */
    private ResourceBundle configFile = ResourceBundle.getBundle("properties.Config");
    /**
     * The closing key that, when entered, triggers the server closure.
     */
    private String closingKey = configFile.getString("CLOSING_KEY");
    /**
     * A flag indicating whether the close loop should continue waiting for input.
     */
    private boolean closeLoop = false;
    /**
     * Logger for logging messages related to the CloseThread class.
     */
    private final static Logger LOGGER = Logger.getLogger(CloseThread.class.getName());
     /**
     * Constructs a CloseThread object.
     */

    public CloseThread() {
    }
/**
     * Listens for the specified closing key input to initiate the server closure.
     * Upon receiving the closing key, it sets the server to close, closes all connections,
     * and exits the application.
     */
    @Override
    public synchronized void run() {
    //public synchronized void start() {        
        while(!closeLoop){
            Scanner scanner = new Scanner(System.in);
            Logger.getLogger(WorkerThread.class.getName()).info("Presiona "+ closingKey + " y presiona Enter para cerrar el servidor.");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase(closingKey)) {
                try {
                    Server.setClose(true);
                    Pool.getPool().closeAllConnection();
                    closeLoop = true;
                    Logger.getLogger(WorkerThread.class.getName()).info("Cerrando Servidor");
                    exit(0);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CloseThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CloseThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                Logger.getLogger(WorkerThread.class.getName()).info("Input de cerrado incorrecto");
            }
        }
       
    }
}
