package model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class KeyManager {
    
    private final Integer iterations;
    private final String salt;
    private final String hash;
    private HashMap<String, User> users;    
  
    public KeyManager(Integer iterations, String salt, String hash) {
        this.iterations = iterations;
        this.salt = salt;
        this.hash = hash;
        this.users = new HashMap<>();
    }

    public Integer getIterations() {
        return iterations;
    }

    public String getSalt() {
        return salt;
    }

    public String getHash() {
        return hash;
    }
    
    public HashMap<String, User> getUsers() {
        return users;
    }
    
    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }
    

    
    @Override
    public String toString() {
        return this.iterations.toString() + ":" + this.salt + ":" + this.hash;
    }    

    
}
