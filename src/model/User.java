package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private final String derivedKey;
    private final String salt;
    private final String IV;
    private final ArrayList<String> messages;
    
    
    private static final long serialVersionUID = 1L;
    
    public User(String username, String derivedKey, String salt, String IV, ArrayList<String> messages) {
        this.username = username;
        this.derivedKey = derivedKey;
        this.salt = salt;
        this.IV = IV;
        this.messages = messages;
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
    
    public String getDerivedKey() {
        return this.derivedKey;
    }
        
    public String getSalt() {
        return this.salt;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
    
    private String formatMessages() {
        String list = this.getMessages().toString();
        return list.substring(1, list.length()- 1).trim();
    }
 
    @Override
    public String toString() {
        String userString = this.username + ":" + this.derivedKey + ":" + this.salt + ":" + this.IV + ":" + this.formatMessages();
        return userString.trim();
    }
}
