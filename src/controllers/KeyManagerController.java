package controllers;

import exceptions.UnauthorizedException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import model.KeyManager;
import model.User;
import utils.FileManipulator;
import utils.PBKDF2UtilBCFIPS;

public class KeyManagerController {
    private static KeyManagerController instance;
    private final FileManipulator file = new FileManipulator();
    
    public static KeyManagerController getInstance() {
        if (instance == null) {
            instance = new KeyManagerController();
        }
        return instance;
    }
    
    private boolean validatePassword(String typedPassword, Integer iterations, String salt, String savedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        String derivedKey = PBKDF2UtilBCFIPS.getInstance().generateDerivedKey(typedPassword, salt, iterations);
        return derivedKey.equals(savedPassword); 
    }
    
    public KeyManager create(String password, String path) throws NoSuchAlgorithmException, IOException {
        int iterations = 30000;
        String salt = PBKDF2UtilBCFIPS.getInstance().getSalt();
        String derivatedKey = PBKDF2UtilBCFIPS.getInstance().generateDerivedKey(password, salt, iterations);
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
        keyManager.setUsers(loadUsers("users.txt"));
        return keyManager;       
    }
    
    private HashMap<String, User> loadUsers(String path) throws IOException {
        HashMap<String, User> users = new HashMap<>();
        ArrayList<String> dataUsers = file.reader(path);
        for (String user : dataUsers) {
            if(user != null) {
                String [] userSplitted = user.split(":");
                String username = userSplitted[0];
                String password = userSplitted[1];
                String salt = userSplitted[2];
                String iv = userSplitted[3];
                users.put(username, new User(username, password, salt, iv));
            }
        }
        return users;
    }
    
    public void login(String username, String password) {
        
        
    }
}
