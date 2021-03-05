package controlllers;

import java.security.NoSuchAlgorithmException;
import model.User;
import security.PBKDF2UtilBCFIPS;

public class UserController {
    private static UserController instance;
    private PBKDF2UtilBCFIPS pbkf2 = new PBKDF2UtilBCFIPS();
    
    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }
    
    public void createUser(String username, String password) throws NoSuchAlgorithmException {
        // verificar se o usuário já existe
        Integer iterations = 10000;
        String salt = pbkf2.getSalt();
        String cipherPassword = pbkf2.generateDerivedKey(password, salt, iterations);
        User user = new User(username, cipherPassword);
        // salva esse usuário em algum lugar
    }
    
    private void validatePassword(String password, String cipherPassword) {
        
        
    }
    
    public void login(String username, String password) {
        // verificar se o usuário já existe
        
        // chama validate password
        
        // compara se a hash da senha infomada é igual a hash salvada no banco
        
    }
    
    public void sendMessage (User destination, String cipherText) {
        
    }

}
