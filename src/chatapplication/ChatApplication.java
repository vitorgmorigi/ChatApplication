package chatapplication;

//import org.apache.commons.codec.binary.Hex;

import controllers.KeyManagerController;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Scanner;
import javax.crypto.SecretKey;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import utils.PBKDF2UtilBCFIPS;
import controllers.UserController;
import exceptions.UnauthorizedException;
import java.io.File;
import model.KeyManager;

public class ChatApplication {

    /**
     * @param args the command line arguments
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
                typedMasterPassword = input.nextLine();
                keyManager = keyManagerController.load(typedMasterPassword, masterKeyPath);
            } else {
                System.out.println("Create your master password: ");
                typedMasterPassword = input.nextLine();
                keyManager = keyManagerController.create(typedMasterPassword, masterKeyPath);
            }
        
            String username;
            String password;
        
            System.out.println("Enter username: ");
            username = input.nextLine();
        
            System.out.println("Enter password: ");
            password = input.nextLine();
        
            userController.createUser(username, password);   
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
}
