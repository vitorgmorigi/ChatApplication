package controllers;

import exceptions.UnauthorizedException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import model.KeyManager;
import org.apache.commons.codec.binary.Hex;
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
    
    private boolean validatePassword(String typedPassword, Integer iterations, String salt, String savedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        String derivedKey = pbkf2.generateDerivedKey(typedPassword, salt, iterations);
        return derivedKey.equals(savedPassword); 
    }
    
    public KeyManager create(String password, String path) throws NoSuchAlgorithmException, IOException {
        int iterations = 30000;
        String salt = pbkf2.getSalt();
        String derivatedKey = pbkf2.generateDerivedKey(password, salt, iterations);
        KeyManager keyManager = new KeyManager(iterations, salt, derivatedKey);
        file.writer(path, keyManager.toString());
        return keyManager;
    }
    
    public KeyManager load(String password, String path) throws UnauthorizedException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        ArrayList<String> masterKeyList = file.reader(path);
        String masterKeyData = masterKeyList.get(0);
        String [] dataSplitted = masterKeyData.split(":");
        Integer iterations = Integer.parseInt(dataSplitted[0]);
        String salt = dataSplitted[1];
        String hash = dataSplitted[2];
        
        boolean isValid = validatePassword(password, iterations, salt, hash);
        if(!isValid) {
            throw new UnauthorizedException("The password is incorrect");
        }
        KeyManager keyManager = new KeyManager(iterations, dataSplitted[1], dataSplitted[2]);
        return keyManager;       
    }
}
