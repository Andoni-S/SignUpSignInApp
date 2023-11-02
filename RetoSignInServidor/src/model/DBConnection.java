package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The DBConnection class provides methods for establishing and managing a
 * database connection using configuration parameters retrieved from a resource
 * bundle.
 *
 * The class initializes the connection parameters (URL, username, password)
 * from a "application.Config" resource bundle and allows clients to open and
 * close database connections.
 *
 * @author Andoni Sanz
 */
public class DBConnection {

    private ResourceBundle configFile;
    private String url;
    private String user;
    private String pass;

    /**
     * Constructs a new DBConnection object by reading the database connection
     * parameters from a "application.Config" resource bundle.
     */
    public DBConnection() {
        configFile = ResourceBundle.getBundle("properties.Config");
        url = configFile.getString("URL");
        user = configFile.getString("USER");
        pass = configFile.getString("PASSWORD");
    }

    /**
     * Opens a new database connection using the configured connection
     * parameters.
     *
     * @return A Connection object representing the database connection, or null
     * if an error occurs.
     */
    public Connection openConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return con;
    }

    /**
     * Closes the specified PreparedStatement and Connection objects.
     *
     * @param stmt The PreparedStatement to be closed.
     * @param con The Connection to be closed.
     */
    public void closeConnection(PreparedStatement stmt, Connection con) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.print(e.getMessage());
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.print(e.getMessage());
            }
        }
    }
}
