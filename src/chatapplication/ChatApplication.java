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
import model.User;
import utils.AESGCMFIPS;

public class ChatApplication {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        UserController userController = UserController.getInstance();
        KeyManagerController keyManagerController = KeyManagerController.getInstance();
        AESGCMFIPS gcmUtils = AESGCMFIPS.getInstance();
        
        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());
        
        String typedMasterPassword;
        String masterKeyPath = "masterkey.txt";
            
        Scanner input = new Scanner(System.in);
        KeyManager keyManager;
        
        File masterKeyFile = new File(masterKeyPath);
        
        String status = "masterkey";
        int typedOption;
        User loggedUser = null;
        
        while(true) {
            try {
                switch(status) {
                    case "masterkey":
                        if(masterKeyFile.exists()) {
                            System.out.println("Enter master password: ");
                            typedMasterPassword = input.next();
                            keyManager = keyManagerController.load(typedMasterPassword, masterKeyPath);
                        } else {
                            System.out.println("Create your master password: ");
                            typedMasterPassword = input.next();
                            keyManager = keyManagerController.create(typedMasterPassword, masterKeyPath);
                        }
                        status = "login";
                        break;
                    case "login":
                        System.out.println("Digite a opção desejada: ");
                        System.out.println("1 - Cadastrar usuário");
                        System.out.println("2 - Login");
                        System.out.println("5 - Sair");

                        typedOption = input.nextInt();

                        String username;
                        String password;

                        switch (typedOption) {
                            case 1:
                                System.out.println("Enter username: ");
                                username = input.next();
                                System.out.println("Enter password: ");
                                password = input.next();
                                keyManager = keyManagerController.createUser(username, password);
                                break;
                            case 2:
                                System.out.println("Enter username: ");
                                username = input.next();
                                System.out.println("Enter password: ");
                                password = input.next();
                                loggedUser = keyManagerController.login(username, password);
                                status = "logged";
                                break;
                            default:
                                status = "quit";
                                break;
                        }
                        break;

                    case "logged":
                        System.out.println("3 - Escrever uma mensagem");
                        System.out.println("4 - Ler mensagens");
                        System.out.println("5 - Logout");
                        typedOption = input.nextInt();
                        switch(typedOption) {
                            case 3:
                                System.out.println("Digite o destinatário: ");
                                String usernameDestinatary = input.next();
                                User userDestinatary = keyManagerController.getUserByUsername(usernameDestinatary);
                                System.out.println("Digite a mensagem: ");
                                String message = input.next();
                                keyManager = keyManagerController.sendMessage(userDestinatary, message);
                                break;

                            case 4:
                                System.out.println(keyManagerController.readMessages(loggedUser));
                                break;
                                
                            default:
                                status = "login";
                                break;
                        }
                        break;

                }
                if(status.equals("quit"))
                    break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }   
            
        }
        
        keyManagerController.persistOnFile();
    }
    
}
