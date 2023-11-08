package Threads;

import connection.Pool;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Server;

public class CloseThread extends Thread {
    
    private ResourceBundle configFile = ResourceBundle.getBundle("properties.Config");
    private String closingKey = configFile.getString("CLOSING_KEY");
    private boolean closeLoop = false;
    public CloseThread() {
    }

    @Override
    public synchronized void start() {
        
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
                    interrupt();
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
