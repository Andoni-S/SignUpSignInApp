/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jagoba Bartolomé Barroso
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
        configFile = ResourceBundle.getBundle("logicTier.config");
        url = configFile.getString("URL");
        user = configFile.getString("USER");
        pass = configFile.getString("PASSWORD");
        driver = configFile.getString("DRIVER");
        
        Class.forName(driver);
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
        if (connectionStack.isEmpty()){
            Connection con = DriverManager.getConnection(url, user, pass);
            connectionStack.push(con);
        }
        return connectionStack.pop();
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
