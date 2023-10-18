package main;

import Threads.DeclineThread;
import Threads.WorkerThread;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PUERTO = 5004;
    private int clienteN = 0;
    private final int  MAXIMUM_CLIENTS = 2;
    public void iniciar() {
        ServerSocket servidor = null;
        Socket cliente = null;
        ObjectInputStream entrada = null;
        ObjectOutputStream salida = null;
        try {
            servidor = new ServerSocket(PUERTO);
            while (true) {
                           
                System.out.println("Esperando conexiones del cliente...");
                cliente = servidor.accept();
                System.out.println("Cliente conectado");
                
                if(clienteN < MAXIMUM_CLIENTS){
                    WorkerThread wt = new WorkerThread(cliente);
                    clienteN++;
                    wt.start();
                    
                } else{
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