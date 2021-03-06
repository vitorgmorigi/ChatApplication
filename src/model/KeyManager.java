package model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class KeyManager {
    
    private final Integer iterations;
    private final String salt;
    private final String hash;
    private final HashMap<String, User> users;
    
    
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
    

    
    @Override
    public String toString() {
        return this.iterations.toString() + ":" + this.salt + ":" + this.hash;
    }
    
    
    
}
