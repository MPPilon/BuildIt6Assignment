/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildit;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author c0538434
 */
public class DBUtils {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String jdbc = "jdbc:mysql://IPRO/Feb132017";
        String username = "Feb132017";
        String password = "Feb132017";
        
        return DriverManager.getConnection(jdbc, username, password);
    }
}
