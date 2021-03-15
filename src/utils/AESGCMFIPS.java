package utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import org.apache.commons.codec.binary.Hex;

public class AESGCMFIPS {
    private static AESGCMFIPS instance;
    private static final int MAC_SIZE = 128; // in bits
    private static final String digits = "0123456789abcdef";
    
    // AES-GCM parameters
    public static final int AES_KEY_SIZE = 128; // in bits
    public static final int GCM_NONCE_LENGTH = 16; // in bytes
    public static final int GCM_TAG_LENGTH = 16; // in bytes
    
    public static AESGCMFIPS getInstance() {
        if (instance == null) {
            instance = new AESGCMFIPS();
        }
        return instance;
    }
        
    public String encryptMessage(String derivedKey, String nonce, String message) throws Exception {
        Key key;
        Cipher in;

        in = Cipher.getInstance("AES/GCM/NoPadding", "BCFIPS");

        key = new SecretKeySpec(Hex.decodeHex(derivedKey.toCharArray()), "AES");
        
        GCMParameterSpec​ gcmParameters = new GCMParameterSpec​(MAC_SIZE, Hex.decodeHex(nonce.toCharArray()));

        in.init(Cipher.ENCRYPT_MODE, key, gcmParameters);

        byte[] enc = in.doFinal(message.getBytes());
         
        String cipherMessage = toHex(enc, enc.length);
        
        return cipherMessage;
    }
    
    public String decryptMessage(String derivedKey, String nonce, String cipherMessage) throws Exception {
        Key key;
        Cipher out;

        out = Cipher.getInstance("AES/GCM/NoPadding", "BCFIPS");

        key = new SecretKeySpec(Hex.decodeHex(derivedKey.toCharArray()), "AES");
        
        GCMParameterSpec​ gcmParameters = new GCMParameterSpec​(MAC_SIZE, Hex.decodeHex(nonce.toCharArray()));

        out.init(Cipher.DECRYPT_MODE, key, gcmParameters);

        byte[] dec = out.doFinal(Hex.decodeHex(cipherMessage.toCharArray()));
        
        String decipherMessage = toString(dec, dec.length);
        
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
    
    private static String toHex(byte[] data, int length)
    {
        StringBuffer	buf = new StringBuffer();
        
        for (int i = 0; i != length; i++)
        {
            int	v = data[i] & 0xff;
            
            buf.append(digits.charAt(v >> 4));
            buf.append(digits.charAt(v & 0xf));
        }
        
        return buf.toString();
    }

}
