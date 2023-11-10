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
import java.util.logging.Logger;

/**
 * This class manages a stack of connection to give to the server as it is needed.
 * @author Jagoba Bartolomé Barroso, Andoni Sanz
 */
public final class Pool {

    private static Pool pool;
    private ResourceBundle configFile;
    private String url, user, pass, driver;
    private static Stack<Connection> connectionStack = new Stack();
    private final static Logger LOGGER = Logger.getLogger(Pool.class.getName());
    /**
     * Class constructor
     */
    public Pool() throws ClassNotFoundException {
        configFile = ResourceBundle.getBundle("properties.Config");
        url = configFile.getString("URL");
        user = configFile.getString("USER");
        pass = configFile.getString("PASSWORD");

    }
    /**
     * Pool getter
     * @return
     */
    public static synchronized Pool getPool() throws ClassNotFoundException{
        if(pool == null){
            pool = new Pool();
        }       
        return pool;
    }
    /**
     * Pool setter
     * @param pool 
     */
    public static synchronized void setPool(Pool pool) {
        Pool.pool = pool;
    }
    /**
     * Getter of the connection. Checks if the stack is empty and then creates a new one, otherwise it just returns the Connection
     * @return
     * @throws SQLException 
     */
    public synchronized Connection getConnection() throws SQLException{
        Connection con;
        if (connectionStack.isEmpty()){
            con = DriverManager.getConnection(url, user, pass);      
            LOGGER.info("Returned a new connection.");
        }else{
            con =connectionStack.pop();
            LOGGER.info("Returned a connection from the stack.");
        }
        return con;
    }   
    /**
     * 
     * @param con 
     */
    public synchronized void saveConnection(Connection con){
            connectionStack.push(con);
            LOGGER.info("Saved a used connection.");
    }
    
     public synchronized void closeAllConnection() throws SQLException{
         for(Connection con : connectionStack){
            con.close();
            LOGGER.info("Closed connections.");
         }
     }
}
