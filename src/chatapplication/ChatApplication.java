package chatapplication;

//import org.apache.commons.codec.binary.Hex;

import controllers.KeyManagerController;
import java.security.Security;
import java.util.Scanner;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import utils.PBKDF2UtilBCFIPS;
import controllers.UserController;
import java.io.File;
import model.KeyManager;

public class ChatApplication {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        PBKDF2UtilBCFIPS obj = new PBKDF2UtilBCFIPS();     
        UserController userController = new UserController();
        KeyManagerController keyManagerController = new KeyManagerController();
        
        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());
        
        String typedMasterPassword;
        String masterKeyPath = "masterkey.txt";
            
        Scanner input = new Scanner(System.in);
        KeyManager keyManager;
        
        File masterKeyFile = new File(masterKeyPath);
        
        try {
            if(masterKeyFile.exists()) {
                System.out.println("Enter master password: ");
                typedMasterPassword = input.next();
                keyManager = keyManagerController.load(typedMasterPassword, masterKeyPath);
            } else {
                System.out.println("Create your master password: ");
                typedMasterPassword = input.next();
                keyManager = keyManagerController.create(typedMasterPassword, masterKeyPath);
            }
            
            System.out.println("Digite a opção desejada: ");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Login");
            
            int opcao = input.nextInt();
            
            if(opcao == 1) {
                String username;
                String password;
        
                System.out.println("Enter username: ");
                username = input.next();
        
                System.out.println("Enter password: ");
                password = input.next();
        
                userController.createUser(username, password);  
            } else {
                
            }
        
 
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
}
