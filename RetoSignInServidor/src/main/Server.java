package main;

import Threads.DeclineThread;
import Threads.WorkerThread;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import libraries.User;

public class Server {
    
    //declarar resource bundle
    private ResourceBundle configFile = ResourceBundle.getBundle("properties.Config");
    //puerto
    private final int PUERTO = 5004;
    //numero clientes
    private int clienteN = 0;
    //clientes maximos
    private final int  MAXIMUM_CLIENTS = Integer.valueOf(configFile.getString("MAXCLIENTS"));

    public void iniciar() {
        
        ServerSocket servidor = null;
        Socket cliente = null;
        ObjectInputStream entrada = null;
        ObjectOutputStream salida = null;
        try {
            //Instanciar ServerSocket
            servidor = new ServerSocket(PUERTO);
            System.out.println("MAXIMUM CLIENTS: "+ MAXIMUM_CLIENTS);
            
            //ciclo de admision de clientes
            while (true) {
                
                User user = new User();
                //aceptar un cliente en el servidor
                System.out.println("Esperando conexiones del cliente...");
                cliente = servidor.accept();
                System.out.println("Cliente conectado");
                
                //si el numero de clientes no ha llegado al maximo
                if(clienteN < MAXIMUM_CLIENTS){
                    //crear un worker thread donde le permita hacer las operaciones de SignIn/SignUp
                    WorkerThread wt = new WorkerThread(cliente);
                    clienteN++;
                    wt.start();
                    
                } else{ //en caso de que haya llegado al numero maximo de clientes crear un thread que rechace al cliente
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
                if (entrada != null)
                    entrada.close();
                if (salida != null)
                    salida.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Fin servidor");
        }
    }

    public static void main(String[] args) {
        Server s1 = new Server();
        s1.iniciar();
    }
}