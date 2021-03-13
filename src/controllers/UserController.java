package controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
    
    public void createUser(String username, String password) throws NoSuchAlgorithmException, IOException {
        // verificar se o usuário já existe
        Integer iterations = 30000;
        String salt = PBKDF2UtilBCFIPS.getInstance().getSalt();
        String cipherPassword = PBKDF2UtilBCFIPS.getInstance().generateDerivedKey(password, salt, iterations);
        String iv = PBKDF2UtilBCFIPS.getInstance().getSalt();
        User user = new User(username, cipherPassword, salt, iv);
        String textFile = user.toString();
        file.writer("users.txt", textFile);
        
    }
    
//    private void validatePassword(String password, String cipherPassword) {
//        
//        
//    }
    
    public void login(String username, String password) {
        // verificar se o usuário já existe
        
        // chama validate password
        
        // calcula o PBKDF2 da senha informada pelo usuário
        
        // compara se a hash da senha infomada é igual a hash salvada no banco
        
    }
    
    public void sendMessage (User destination, String cipherText) {
        
    }

}
