package controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import model.User;
import utils.*;

public class UserController {
    private static UserController instance;
    private final FileManipulator file = new FileManipulator();
    
    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }
    
    public User createUser(String username, String password) throws NoSuchAlgorithmException, IOException {
        Integer iterations = 30000;
        String salt = PBKDF2UtilBCFIPS.getInstance().getSalt();
        String cipherPassword = PBKDF2UtilBCFIPS.getInstance().generateDerivedKey(password, salt, iterations);
        String iv = PBKDF2UtilBCFIPS.getInstance().getSalt();
        User user = new User(username, cipherPassword, salt, iv, new ArrayList<>());
        return user;
    }
   
}
