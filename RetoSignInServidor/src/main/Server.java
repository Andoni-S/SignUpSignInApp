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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    //declare resource bundle
    private ResourceBundle configFile = ResourceBundle.getBundle("properties.Config");
    //Port to listen to
    private final int PUERTO = 6666;
    //client number
    private static int clienteN = 0;
    //maximum clients
    private final int  MAXIMUM_CLIENTS = Integer.valueOf(configFile.getString("MAXCLIENTS"));

    private static boolean close = false;
    
    public void iniciar() {
        
        ServerSocket servidor = null;
        Socket cliente = null;
        
        try {
            CloseThread ct = new CloseThread();
            ct.start();
            
            Pool pool = Pool.getPool();
                       
            //Instance ServerSocket
            servidor = new ServerSocket(PUERTO);
            Logger.getLogger(WorkerThread.class.getName()).info("MAXIMUM CLIENTS: "+ MAXIMUM_CLIENTS);       
                      
            //cicle of admission of new clients
            while (!close) {
                              
                User user = new User();
                //aceptar un cliente en el servidor
                Logger.getLogger(WorkerThread.class.getName()).info("Esperando conexiones del cliente...");       
                cliente = servidor.accept();
                Logger.getLogger(WorkerThread.class.getName()).info("Cliente "+clienteN+" conectado");       
                
                //If the number of clients has not reached the maximum
                if(clienteN < MAXIMUM_CLIENTS){
                    //create a worker thread where SignIn/SignUp operations are done
                                           
                    WorkerThread wt = new WorkerThread(cliente);
                    incrementClienteN();
                    
                    wt.start();
                    
                } else{ //In case you have reached the maximum number of clients create a thread to reject the client.
                   DeclineThread dt = new DeclineThread(cliente);
                   dt.start();
                }
                Logger.getLogger(WorkerThread.class.getName()).info("Numero de cliente: " + clienteN);           
            }          
        } catch (IOException e) {
            Logger.getLogger(WorkerThread.class.getName()).severe("Error: " + e.getMessage());     
        } catch (Exception e) {
            Logger.getLogger(WorkerThread.class.getName()).severe("Error: " + e.getMessage());     
        } finally {
            try {
                if (servidor != null)
                    servidor.close();
                if (cliente != null)
                    cliente.close();
               Logger.getLogger(WorkerThread.class.getName()).info("Fin servidor");
            } catch (IOException e) {
               e.printStackTrace();
               Logger.getLogger(WorkerThread.class.getName()).severe("Error: " + e.getMessage());     
            }
        }
    }
    public synchronized static int incrementClienteN() {
        return clienteN++;
    }

    public synchronized static int decrementClienteN() {
        return clienteN--;
    }
    
    public synchronized static int getClienteN() {
        return clienteN;
    }

    public synchronized static void setClienteN(int clienteN) {
        Server.clienteN = clienteN;
    }
    
    public synchronized static void setClose(boolean close) {
        Server.close = close;
    }
        
    public static void main(String[] args) {
            Server s1 = new Server();
            s1.iniciar();
    }   
}