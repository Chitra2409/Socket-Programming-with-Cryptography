package chat;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryDecry {
    private static SecretKeySpec secretKey;
    private CustomArray key; // Assuming you have a CustomArray class as mentioned before
    private static final String ALGORITHM = "AES";

    public EncryDecry() {
        key = new CustomArray(16);
    }

    public void prepareSecretKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = new CustomArray(16);
            key.add(myKey.getBytes(StandardCharsets.UTF_8));
            sha = MessageDigest.getInstance("SHA-1");
            byte[] keyBytes = keyToArray(key);
            key = new CustomArray(16);
            key.add(sha.digest(keyBytes));
            secretKey = new SecretKeySpec(keyToArray(key), ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String strToEncrypt, String secret) {
        try {
            prepareSecretKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt, String secret) {
        try {
            prepareSecretKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error decrypting: " + e.toString());
        }
        return null;
    }

    private byte[] keyToArray(CustomArray customKey) {
        byte[] keyBytes = new byte[customKey.size()];
        for (int i = 0; i < customKey.size(); i++) {
            keyBytes[i] = customKey.get(i);
        }
        return keyBytes;
    }
}
