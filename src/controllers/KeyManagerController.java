package controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import model.KeyManager;
import utils.FileManipulator;
import utils.PBKDF2UtilBCFIPS;

public class KeyManagerController {
    private static KeyManagerController instance;
    private PBKDF2UtilBCFIPS pbkf2 = new PBKDF2UtilBCFIPS();
    private FileManipulator file = new FileManipulator();
    
    public static KeyManagerController getInstance() {
        if (instance == null) {
            instance = new KeyManagerController();
        }
        return instance;
    }
    
    public KeyManager create(String password) throws NoSuchAlgorithmException, IOException {
        int iterations = 30000;
        String salt = pbkf2.getSalt();
        String derivatedKey = pbkf2.generateDerivedKey(password, salt, iterations);
        KeyManager keyManager = new KeyManager(iterations, salt, derivatedKey);
        file.writer("masterkey.txt", keyManager.toString());
        return keyManager;
    }
    
    public void load(String path) {
        
        
    }
}
