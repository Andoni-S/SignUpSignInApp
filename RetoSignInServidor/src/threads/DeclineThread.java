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
 * The DeclineThread class represents a thread for handling rejected client
 * connections. It sends an error message to the client when the server reaches
 * the maximum client limit.
 *
 * @author Andoni Sanz
 */
public class DeclineThread extends Thread {

    /**
     * The client socket to which the rejection message will be sent.
     */
    Socket cliente = null;
    /**
     * ObjectInputStream for reading objects from the client.
     */
    ObjectInputStream entrada = null;
    /**
     * ObjectOutputStream for writing objects to the client.
     */
    ObjectOutputStream salida = null;
    /**
     * Logger for logging messages related to the DeclineThread class.
     */
    private final static Logger LOGGER = Logger.getLogger(DeclineThread.class.getName());

    public DeclineThread(Socket cliente) {
        this.cliente = cliente;
    }

    /**
     * run method that implements the thread
     *
     */
    @Override
    public synchronized void run() {
        //public synchronized void start() {        
        LOGGER.info("Launching thread");
        try {

            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());

            Logger.getLogger(DeclineThread.class.getName()).info("Client limite reached.");
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
