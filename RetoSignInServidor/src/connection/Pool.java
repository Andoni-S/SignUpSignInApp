/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jagoba Bartolomé Barroso, Andoni Sanz
 */
public final class Pool {

    private static Pool pool;
    private ResourceBundle configFile;
    private String url, user, pass, driver;
    private static Stack<Connection> connectionStack = new Stack();
    
    /**
     * 
     * @throws ClassNotFoundException 
     */
    public Pool() throws ClassNotFoundException {
        configFile = ResourceBundle.getBundle("properties.Config");
        url = configFile.getString("URL");
        user = configFile.getString("USER");
        pass = configFile.getString("PASSWORD");

    }
    public static Pool getPool() throws ClassNotFoundException {
        if(pool == null){
            pool = new Pool();
        }       
        return pool;
    }

    public static void setPool(Pool pool) {
        Pool.pool = pool;
    }
    /**
     * 
     * @return
     * @throws SQLException 
     */
    public synchronized Connection getConnection() throws SQLException{
        Connection con;
        if (connectionStack.isEmpty()){
            con = DriverManager.getConnection(url, user, pass);           
        }else{
            con =connectionStack.pop();
        }

        return con;
    }   
    /**
     * 
     * @param con 
     */
    public synchronized void saveConnection(Connection con){
            connectionStack.push(con);
    }
    
     public synchronized void closeAllConnection() throws SQLException{
         for(Connection con : connectionStack){
             con.close();
         }
     }
}
