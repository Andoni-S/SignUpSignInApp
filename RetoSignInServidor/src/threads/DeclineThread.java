/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import libraries.ApplicationPDU;
import libraries.MessageType;

/**
 *
 * @author 2dam
 */
public class DeclineThread extends Thread {

    //ServerSocket servidor = null;
    Socket cliente = null;
    ObjectInputStream entrada = null;
    ObjectOutputStream salida = null;

    public DeclineThread(Socket cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public synchronized void start() {
        System.out.println("lanzando thread");
        try {
            
        salida = new ObjectOutputStream(cliente.getOutputStream());
        entrada = new ObjectInputStream(cliente.getInputStream());
           
        Logger.getLogger(DeclineThread.class.getName()).info("limite de clientes alcanzado");
        ApplicationPDU pdu = new ApplicationPDU();
        pdu.setMessageType(MessageType.Ex_ServerError);
        salida.writeObject(pdu);
        
        entrada.close();
        salida.close();
        } catch (IOException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
