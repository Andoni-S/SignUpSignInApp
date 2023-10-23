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
    int localPort = 9000;
    int remotePort = 5432;
    
    public void connectSSH(){
        try{
            
            JSch jsch = new JSch();
            Session session = jsch.getSession(sshUsername, sshHost, 22);
            session.setPassword("abcd*1234");

            // Establecer una propiedad para evitar comprobar la clave de host
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("Compression", "yes");
            session.connect();

            // Crear el túnel SSH
            localPort = session.setPortForwardingL(localPort, remoteHost, remotePort);

            System.out.println("Túnel SSH establecido. Puedes acceder al servidor remoto a través del puerto local " + localPort);

            // Aquí puedes agregar tu lógica para utilizar el túnel SSH

            // Cerrar la sesión cuando hayas terminado
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
