/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 *
 * @author 2dam
 */
public class Pool {

    private ResourceBundle configFile;
    private String url, user, pass, driver;
    private static Stack<Connection> connectionStack = new Stack();
            
    public Pool() {
        configFile = ResourceBundle.getBundle("logicTier.config");
        url = configFile.getString("URL");
        user = configFile.getString("USER");
        pass = configFile.getString("PASSWORD");
        driver = configFile.getString("DRIVER");
    }
}
