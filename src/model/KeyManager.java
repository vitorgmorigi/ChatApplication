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
    
    
    public KeyManager(int iterations, String salt, String hash) {
        this.iterations = iterations;
        this.salt = salt;
        this.hash = hash;
        this.users = new HashMap<>();
    }

    public int getIterations() {
        return iterations;
    }

    public String getSalt() {
        return salt;
    }

    public String getHash() {
        return hash;
    }
    
    public boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, 128);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] testHash = skf.generateSecret(spec).getEncoded();
         
        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++){
            diff |= hash[i] ^ testHash[i];
        }
    
        return diff == 0; 
    }
    
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    
    @Override
    public String toString() {
        return this.iterations.toString() + ":" + this.salt + ":" + this.hash;
    }
    
    
    
}
