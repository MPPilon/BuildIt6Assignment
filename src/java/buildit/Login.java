/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildit;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author c0538434
 */
@Named
@SessionScoped
public class Login implements Serializable {

    private String username;
    private String password;
    private boolean loggedIn;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String login() {
        for (User u : new Users().getUsers()) {
            if (username.equals(u.getUsername())
                    && password.equals(u.getPasshash())) {
                loggedIn = true;
                return "index";
            }
        }
        loggedIn = false;
        return "index";
    }
}
