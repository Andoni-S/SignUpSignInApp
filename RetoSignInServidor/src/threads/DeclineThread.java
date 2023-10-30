/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import libraries.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void run() {
        System.out.println("lanzando thread");
        try {
            
        salida = new ObjectOutputStream(cliente.getOutputStream());
        entrada = new ObjectInputStream(cliente.getInputStream());
       
        
        salida.writeObject("jaja no entras");
        
        
        } catch (IOException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
