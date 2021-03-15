package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.internal.CryptoException;

public class FileManipulator {

    private static FileManipulator instance;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private File managerFile = createOrGetFile(Constants.GERENCIADOR_FILE.getValue());
    private File encrypted = createOrGetFile(Constants.GERENCIADOR_ENCRYPTED.getValue());

    public static FileManipulator getInstance() {
        if (instance == null) {
            instance = new FileManipulator();
        }
        return instance;
    }

    public ArrayList<String> reader(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        ArrayList<String> rows = new ArrayList<>();
        String row = "";
        while (true) {
            if (row == null) {
                break;
            }
            row = buffRead.readLine();
            rows.add(row);
        }
        buffRead.close();
        return rows;
    }

    public void writer(String path, String text) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path, true));
        buffWrite.append(text);
        buffWrite.newLine();
        buffWrite.close();
    }
    
    public boolean managerFileExists() {
        return encrypted.exists();
    }

    public File createOrGetFile(String path) {
        return new File(path);
    }

    public void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public void encryptManagerFile(String password) throws Exception {
        encrypt(password, managerFile, new File("gerenciador.encrypted"));
        deleteFile(Constants.GERENCIADOR_FILE.getValue());
    }
    
    public void decryptManagerFile(String password) throws Exception {
        decrypt(password, encrypted, managerFile);
    }

    public static void encrypt(String key, File inputFile, File outputFile)
            throws Exception {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, File inputFile, File outputFile)
            throws Exception {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    private static void doCrypto(int cipherMode, String key, File inputFile,
            File outputFile) throws CryptoException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

}
