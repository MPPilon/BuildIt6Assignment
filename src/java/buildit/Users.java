/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author c0538434
 */
@ApplicationScoped
@Named
public class Users {
    private List<User> users = new ArrayList<>();
    
    public Users() {
        getUsersFromDb();
    }

    public List<User> getUsers() {
        return users;
    }
    
    private void getUsersFromDb() {
        try {
            Connection conn = DBUtils.getConnection();
            
            users = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            
            while (rs.next()) {
                User u = new User(rs.getInt("id"),
                rs.getString("username"),
                rs.getString("passhash"));
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
            users = new ArrayList<>();
        }
    }
    
    public String getUsernameById(int id) {
        for (User u : users) {
            if (u.getId() == id)
                return u.getUsername();
        }
        return null;
    }
}
