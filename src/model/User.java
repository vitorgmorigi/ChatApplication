package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private final String cipherPassword;
    private final String salt;
    private final String IV;
    private final ArrayList<String> messages;
    
    
    private static final long serialVersionUID = 1L;
    
    public User(String username, String password, String salt, String IV) {
        this.username = username;
        this.cipherPassword = password;
        this.salt = salt;
        this.IV = IV;
        this.messages = new ArrayList<>();
    }
    
    public String getUsername() {
        return this.username;
    }

    public String getIV() {
        return IV;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getCipherPassword() {
        return this.cipherPassword;
    }
        
    public String getSalt() {
        return this.salt;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
 
    @Override
    public String toString() {
        return this.username + ":" + this.cipherPassword + ":" + this.salt + ":" + this.IV;
    }
}
