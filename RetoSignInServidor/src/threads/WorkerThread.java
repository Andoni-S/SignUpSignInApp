/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;
import factory.SignableFactory;
import libraries.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import libraries.ApplicationPDU;
import libraries.MessageType;
import libraries.Signable;

/**
 *
 * @author 2dam
 */
public class WorkerThread extends Thread {

    Socket socket = null;
    ObjectInputStream entrada = null;
    ObjectOutputStream salida = null;
    User user = null;
    ApplicationPDU pdu = null;
    public WorkerThread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        System.out.println("lanzando thread");
        try {
            
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());
        
            salida.writeObject("entraste");
        
            pdu = (ApplicationPDU) entrada.readObject();
        
            Signable s = SignableFactory.getSignable();
               
            if(pdu.getMessageType().toString().equalsIgnoreCase("LogIn")){
                System.out.println("Verificando usuario en la base de datos"); 
                user = new User();
                user = s.logIn(pdu.getUser());
                pdu.setMessageType(MessageType.LogIn);
                pdu.setUser(user);
                interrupt();
            
            }else if(pdu.getMessageType().toString().equalsIgnoreCase("SignUp")){
                System.out.println("Registrando usuario en la base de datos");
                user = new User();
                user = s.signUp(pdu.getUser()); 
                pdu.setMessageType(MessageType.SignUp);
                pdu.setUser(user);
                interrupt();
            } else{
                //ESTO NO DEBERIA OCURRIR, SIEMPRE SIGN IN O SIGN UP
                System.out.println("Ocurrio algun tipo de error");
            }          
        } catch (IOException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            pdu = new ApplicationPDU();
            pdu.setMessageType(MessageType.Ex_ClassNotFound);
            interrupt();
        } catch (ServerErrorException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            pdu = new ApplicationPDU();
            pdu.setMessageType(MessageType.Ex_ServerError);
            interrupt();
        } catch (CredentialsException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            pdu = new ApplicationPDU();
            pdu.setMessageType(MessageType.Ex_Credentials);
            interrupt();
        } catch (EmailAlreadyExistsException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            pdu = new ApplicationPDU();
            pdu.setMessageType(MessageType.Ex_EmailAlreadyExists);
            interrupt();
        }
        finally{
            try {
                salida.writeObject(pdu);
            } catch (IOException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
