package controllers;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.AESGCMFIPS;

public class KeyManagerController {
    private static KeyManagerController instance;
    private KeyManager keyManager;
    private final FileManipulator file = new FileManipulator();
    
    private static final int ITERATIONS = 30000;
    
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
        String salt = PBKDF2UtilBCFIPS.getInstance().getSalt();
        String derivatedKey = PBKDF2UtilBCFIPS.getInstance().generateDerivedKey(
                password, 
                salt, 
                KeyManagerController.ITERATIONS
        );
        this.keyManager = new KeyManager(ITERATIONS, salt, derivatedKey);
        file.writer(path, this.keyManager.toString());
        return this.keyManager;
    }
    
    public KeyManager load(String password, String path) throws Exception {
        ArrayList<String> masterKeyList = file.reader(path);
        String masterKeyData = masterKeyList.get(0);
        String [] dataSplitted = masterKeyData.split(":");
        Integer iterations = Integer.parseInt(dataSplitted[0]);
        String salt = dataSplitted[1];
        String hash = dataSplitted[2];
        
        boolean isValid = validatePassword(password, iterations, salt, hash);
        if(!isValid) {
            throw new Exception("Credenciais incorretas!");
        }
        this.keyManager = new KeyManager(iterations, dataSplitted[1], dataSplitted[2]);
        this.keyManager.setUsers(loadUsers("users.txt"));
        return this.keyManager;       
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
                ArrayList<String> listMessages = new ArrayList<>();
                if (userSplitted.length >= 5) {
                    String [] messages = userSplitted[4].split(",");
                    for (String message : messages) {
                        listMessages.add(message);
                    }
                }

                users.put(username, new User(username, password, salt, iv, listMessages));
            }
        }
        return users;
    }
    
    public User getUserByUsername(String username) {
        return this.keyManager.getUsers().get(username);
    }

    public KeyManager createUser(String username, String password) throws Exception {
        boolean userExists = this.keyManager.getUsers().get(username) != null;
        
        if(!userExists) {
            User user = UserController.getInstance().createUser(username, password);
            this.keyManager.getUsers().put(username, user);
            file.writer("users.txt", user.toString());
            return this.keyManager;
        }
        throw new Exception("O usuário já existe!");
    }
    
    public User login(String username, String password) throws Exception {
        User user = this.keyManager.getUsers().get(username);
        if(user != null) {
            boolean passwordCorrect = validatePassword(password, KeyManagerController.ITERATIONS, user.getSalt(), user.getDerivedKey());
            if(passwordCorrect) {
                return user;
            }
        } 
        throw new Exception("Credenciais incorretas!");
            
    }
    
    public KeyManager sendMessage (User destinatary, String message) throws Exception {
        String cipherMessage = AESGCMFIPS.getInstance().encryptMessage(destinatary.getDerivedKey(), destinatary.getIV(), message);
        this.keyManager.getUsers().get(destinatary.getUsername()).getMessages().add(cipherMessage);
        
        return this.keyManager;
    }
    
    public String readMessages(User loggedUser) throws Exception {
        ArrayList<String> decryptedMessages = new ArrayList<>();
        
        for(String message : loggedUser.getMessages()) {
            String decryptedMessage = AESGCMFIPS.getInstance().decryptMessage(loggedUser.getDerivedKey(), loggedUser.getIV(), message);
            decryptedMessages.add(decryptedMessage);
        }
        
        return decryptedMessages.toString();
    }
    
    public void persistOnFile() {
        file.deleteFile("users.txt");
        this.keyManager.getUsers().forEach((username, user) -> {
            try {
                file.writer("users.txt", user.toString());
            } catch (IOException ex) {
                Logger.getLogger(KeyManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
