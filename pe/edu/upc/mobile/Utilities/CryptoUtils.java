package pe.edu.upc.mobile.Utilities;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.CharEncoding;

public class CryptoUtils {
    public static String encryptPassword(String password) throws Exception {
        byte[] encrypted = new byte[0];
        try {
            encrypted = encrypt(password.getBytes(CharEncoding.UTF_8), "0000000000000000".getBytes(CharEncoding.UTF_8), "0000000000000000".getBytes(CharEncoding.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeBytes(encrypted);
    }

    private static byte[] encrypt(byte[] bytes, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        return cipher.doFinal(bytes);
    }
}
