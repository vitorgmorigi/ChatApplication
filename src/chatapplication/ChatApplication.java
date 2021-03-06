package chatapplication;

//import org.apache.commons.codec.binary.Hex;

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
import java.io.File;

public class ChatApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        PBKDF2UtilBCFIPS obj = new PBKDF2UtilBCFIPS();     
        UserController userController = new UserController();
        
        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());
        
        String typedMasterPassword;
            
        Scanner input = new Scanner(System.in);
        
        File masterPasswordFile = new File("masterkey.txt");
        if(masterPasswordFile.exists()) {
            System.out.println("Enter master password: ");
            typedMasterPassword = input.nextLine();
        }
        
        String username;
        String password;
        
        System.out.println("Enter username: ");
        username = input.nextLine();
        
        System.out.println("Enter password: ");
        password = input.nextLine();
        
        userController.createUser(username, password);
        
        
    }
    
}
