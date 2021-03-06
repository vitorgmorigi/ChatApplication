package model;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String cipherPassword;
    private final String salt;
    
    private static final long serialVersionUID = 1L;
    
    public User(String username, String password, String salt) {
        this.username = username;
        this.cipherPassword = password;
        this.salt = salt;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getCipherPassword() {
        return this.cipherPassword;
    }
    
    public void setCipherPassword(String password) {
        this.cipherPassword = password;
    }
    
    public String getSalt() {
        return this.salt;
    }
    
    @Override
    public String toString() {
        return this.username + ":" + this.cipherPassword + ":" + this.salt + "/";
    }
}
