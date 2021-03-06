package chatapplication;

//import org.apache.commons.codec.binary.Hex;

import controllers.KeyManagerController;
import java.security.Security;
import java.util.Scanner;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import controllers.UserController;
import java.io.File;
import model.KeyManager;
import model.User;
import utils.AESGCMFIPS;
import utils.Constants;
import utils.FileManipulator;

public class ChatApplication {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        UserController userController = UserController.getInstance();
        KeyManagerController keyManagerController = KeyManagerController.getInstance();
        AESGCMFIPS gcmUtils = AESGCMFIPS.getInstance();
        FileManipulator file = FileManipulator.getInstance();
        
        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());
        
        String typedMasterPassword = "";
        String masterKeyPath = Constants.MASTERKEY_FILE.getValue();
            
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
                            System.out.println("Digite a senha mestre: ");
                            typedMasterPassword = input.nextLine();
                            keyManager = keyManagerController.load(typedMasterPassword, masterKeyPath);
                        } else {
                            System.out.println("Crie sua senha mestre: ");
                            typedMasterPassword = input.nextLine();
                            keyManager = keyManagerController.create(typedMasterPassword, masterKeyPath);
                        }
                        status = "login";
                        break;
                    case "login":
                        System.out.println("Digite a op????o desejada: ");
                        System.out.println("1 - Cadastrar usu??rio");
                        System.out.println("2 - Login");
                        System.out.println("5 - Sair");

                        typedOption = input.nextInt();
                        input.nextLine();

                        String username;
                        String password;

                        switch (typedOption) {
                            case 1:
                                System.out.println("Enter username: ");
                                username = input.nextLine();
                                System.out.println("Enter password: ");
                                password = input.nextLine();
                                keyManager = keyManagerController.createUser(username, password);
                                break;
                            case 2:
                                System.out.println("Digite o usu??rio: ");
                                username = input.nextLine();
                                System.out.println("Digite a senha: ");
                                password = input.nextLine();
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
                        input.nextLine();
                        switch(typedOption) {
                            case 3:
                                System.out.println("Digite o destinat??rio: ");
                                String usernameDestinatary = input.nextLine();
                                User userDestinatary = keyManagerController.getUserByUsername(usernameDestinatary);
                                System.out.println("Digite a mensagem: ");
                                String message = input.nextLine();
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
                if(status.equals("quit")) {
                    file.encryptManagerFile(keyManagerController.getInstance().getKeyManager().getHash());
                    break;
                }
                    
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }   
            
        }
    }
    
}
