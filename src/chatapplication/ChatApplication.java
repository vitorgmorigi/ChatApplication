import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Scanner;
import javax.crypto.SecretKey;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import security.PBKDF2UtilBCFIPS;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;

/**
 *
 * @author vitor
 */
public class ChatApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        PBKDF2UtilBCFIPS obj = new PBKDF2UtilBCFIPS();     
        
        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());
        
        String username;
        String password;
        String salt;
        int it = 10000;
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter username: ");
        username = input.nextLine();
        
        System.out.println("Enter password: ");
        password = input.nextLine();

        //senha = "123456789";
        salt = obj.getSalt();
        
//        System.out.println("Senha original = " + senha);
//        System.out.println("Sal gerado = " + salt);
//        System.out.println("Numero de iteracoes = " + it);
        
        String chaveDerivada = obj.generateDerivedKey(password, salt, it);
       
        System.out.println("Chave derivada da senha = " + chaveDerivada );
    }
    
}
