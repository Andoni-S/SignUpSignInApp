/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 *
 * @author 2dam
 */
public class SSHConnection {
    
    private String sshHost = "192.168.20.51";
    private String remoteHost = "192.168.10.2";
    private String sshUsername = "ubu";
    private int localPort = 9000;
    private int remotePort = 5432;
    Session session = null;
    
    public void connectSSH(){
        try{
            
            JSch jsch = new JSch();
            session = jsch.getSession(sshUsername, sshHost, 22);
            session.setPassword("abcd*1234");

            // Establecer una propiedad para evitar comprobar la clave de host
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("Compression", "yes");
            session.connect();

            // Crear el túnel SSH
            localPort = session.setPortForwardingL(localPort, remoteHost, remotePort);

            System.out.println("Túnel SSH establecido. Puedes acceder al servidor remoto a través del puerto local " + localPort);

            
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
    
    public void discconectSSH(){
        // Cerrar la sesión cuando hayas terminado
        System.out.println("Desconectando conexión SSH");
        session.disconnect();
    }
}
