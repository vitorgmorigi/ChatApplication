package utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

public class AESGCMFIPS {
    private static final int MAC_SIZE = 128; // in bits
    
    // AES-GCM parameters
    public static final int AES_KEY_SIZE = 128; // in bits
    public static final int GCM_NONCE_LENGTH = 16; // in bytes
    public static final int GCM_TAG_LENGTH = 16; // in bytes
    
        
    public String encryptMessage(String derivedKey, String nonce, String message) throws Exception {
        Key key;
        Cipher in;

        in = Cipher.getInstance("AES/GCM/NoPadding", "BCFIPS");

        key = new SecretKeySpec(derivedKey.getBytes(), "AES");

        in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(nonce.getBytes()));

        byte[] enc = in.doFinal(message.getBytes());
        
        return toString(enc, enc.length);
    }
    
    public String decryptMessage(String derivedKey, String nonce, String cipherMessage) throws Exception {
        Key key;
        Cipher out;

        out = Cipher.getInstance("AES/GCM/NoPadding", "BCFIPS");

        key = new SecretKeySpec(derivedKey.getBytes(), "AES");

        out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(nonce.getBytes()));

        byte[] dec = out.doFinal(cipherMessage.getBytes());
        
        return toString(dec, dec.length);
    }
        
    private static String toString(byte[] bytes, int length)
    {
        char[]	chars = new char[length];
        
        for (int i = 0; i != chars.length; i++)
        {
            chars[i] = (char)(bytes[i] & 0xff);
        }
        
        return new String(chars);
    }

}
