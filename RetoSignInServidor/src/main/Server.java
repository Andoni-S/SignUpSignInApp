package main;

import Threads.DeclineThread;
import Threads.WorkerThread;
import connection.SSHConnection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import libraries.User;
import connection.Pool;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Server {
    
    private static Server server;
    //declare resource bundle
    private ResourceBundle configFile = ResourceBundle.getBundle("properties.Config");
    //Port to listen to
    private final int PUERTO = 6666;
    //client number
    private static int clienteN = 0;
    //maximum clients
    private final int  MAXIMUM_CLIENTS = Integer.valueOf(configFile.getString("MAXCLIENTS"));

    public void iniciar() {
        
        ServerSocket servidor = null;
        Socket cliente = null;
        SSHConnection sshConnection = null;
        
        try {
            sshConnection = new SSHConnection();
            sshConnection.connectSSH();
            
            Pool pool = Pool.getPool();
            
            
            //Instance ServerSocket
            servidor = new ServerSocket(PUERTO);
            System.out.println("MAXIMUM CLIENTS: "+ MAXIMUM_CLIENTS);
                      
            //cicle of admission of new clients
            while (true) {
                                         
                User user = new User();
                //aceptar un cliente en el servidor
                System.out.println("Esperando conexiones del cliente...");
                cliente = servidor.accept();
                System.out.println("Cliente conectado");
                
                //If the number of clients has not reached the maximum
                if(clienteN < MAXIMUM_CLIENTS){
                    //create a worker thread where SignIn/SignUp operations are done
                    WorkerThread wt = new WorkerThread(cliente);
                    clienteN++;
                    
                    wt.start();
                    
                } else{ //In case you have reached the maximum number of clients create a thread to reject the client.
                   DeclineThread dt = new DeclineThread(cliente);
                   dt.start();
                }
                            
                System.out.println("Numero de cliente: " + clienteN);
            }          
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (servidor != null)
                    servidor.close();
                if (cliente != null)
                    cliente.close();
                
                sshConnection.discconectSSH();
               
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Fin servidor");
        }
    }
    
    public static Server getServer() {
        if(server == null){
            server = new Server();
        }       
        return server;
    }
    public static void setPool(Pool pool) {
        Server.server = server;
    }
    public static int getClienteN() {
        return clienteN;
    }

    public static void setClienteN(int clienteN) {
        Server.clienteN = clienteN;
    }
    
    public static void main(String[] args) {
            Server s1 = getServer();
            s1.iniciar();
    }

    
}