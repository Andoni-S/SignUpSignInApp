package model;

import connection.DBConnection;
import connection.Pool;
import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import libraries.Signable;
import libraries.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 2dam
 */
public class SignableImplementation implements Signable{

    /**
     * Registers a new user in the database.
     *
     * @param user The user to register.
     * @return The registered user.
     * @throws ServerErrorException If a server error occurs.
     * @throws EmailAlreadyExistsException If the email address already exists in the database.
     */
    @Override
    public User signUp(User user) throws ServerErrorException, EmailAlreadyExistsException {
       // Paso 1: Conectar a PostgreSQL
        try {
            	
            Connection con;
            Pool pool = Pool.getPool();
            con = pool.getConnection();
            
            if(con == null)
                System.out.println("Error");
           
            //String insertResPartner = "INSERT INTO res_partner DEFAULT VALUES";
            String insertResPartner = "INSERT INTO res_partner (id, street, zip, street2, name, mobile, active) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(insertResPartner, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getStreet());
            pstmt.setString(2, user.getPostalCode());
            pstmt.setString(3, user.getProvince());
            pstmt.setString(4, user.getName());
            pstmt.setString(5, user.getMobilePhone());
            pstmt.setBoolean(6, user.isActive());
            pstmt.executeUpdate();

            // Obtain the ID res_partner
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int partner_id = -1;
            if (generatedKeys.next()) {
                partner_id = generatedKeys.getInt(1);
            }
            
            String insertResUsers = "INSERT INTO res_users (login, password, partner_id, company_id, notification_type) VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(insertResUsers);
            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, partner_id);
            pstmt.setInt(4, 1);
            pstmt.setString(5, user.getNotificationType().toString()); 
            pstmt.executeUpdate();
            
            String insertResGroupUsersRel = "INSERT INTO res_groups_users_rel (uid, gid) VALUES (?, ?),(?, ?),(?, ?),(?, ?))";
            pstmt = con.prepareStatement(insertResGroupUsersRel);
            int userID = getUserIdByLogin(con, "correo2@ejemplo.com");
            pstmt.setInt(1, userID);
            pstmt.setInt(2, 1);
            pstmt.setInt(3, userID);
            pstmt.setInt(4, 7);
            pstmt.setInt(5, userID);
            pstmt.setInt(6, 8);
            pstmt.setInt(7, userID);
            pstmt.setInt(8, 9);
            pstmt.executeUpdate();
            
            String insertResCompanyUsersRel = "INSERT INTO res_company_users_rel (cid, user_id) VALUES (?, ?)";
            pstmt = con.prepareStatement(insertResCompanyUsersRel);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();
            
            pstmt.close();
            
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            return null;
        }
    }
    
    /**
     * Logs in a user to the application.
     *
     * @param user The user attempting to log in.
     * @return The user who has logged in if authentication is successful.
     * @throws ServerErrorException If a server error occurs.
     * @throws CredentialsException If login credentials are incorrect.
     */
    @Override
    public User logIn(User user) throws ServerErrorException, CredentialsException {
        try {
        Connection con;
        Pool pool = Pool.getPool();
        con = pool.getConnection();
            
        String selectUser = "SELECT * FROM res_users WHERE login = ? AND password = ?";
        PreparedStatement pstmt;
        
        pstmt = con.prepareStatement(selectUser);
       
        pstmt.setString(1, user.getLogin());
        pstmt.setString(2, user.getPassword());
        
        ResultSet result = pstmt.executeQuery();

        if (result.next()) {
            //TODO CREAR OBJETO USER
            System.out.println("Usuario y contraseña válidos.");
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }

        result.close();
        pstmt.close();
            
        return new User();
        
        } catch (SQLException ex) {
            Logger.getLogger(SignableImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignableImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Retrieves the ID of a user by their login name.
     *
     * @param connection The database connection.
     * @param login The user's login name.
     * @return The user's ID if found, or -1 if not found.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    private static int getUserIdByLogin(Connection connection, String login) throws SQLException {
        String query = "SELECT id FROM res_users WHERE login = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, login);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return -1;
    }
   
}
