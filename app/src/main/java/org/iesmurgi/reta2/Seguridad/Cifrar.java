package org.iesmurgi.reta2.Seguridad;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cifrar {
    private static String secretKey = "rw82[8N-&Nu=AN9X9c{9Tvx$";

    public static String decrypt(String encryptedText) throws Exception {
        byte[] message = Base64.decode(encryptedText.getBytes(), Base64.DEFAULT);
        Cipher decipher = Cipher.getInstance("DESede");
        decipher.init(Cipher.DECRYPT_MODE, getSecreteKey(secretKey));
        byte[] texto = decipher.doFinal(message);
        return new String(texto, "UTF-8");
    }

    public static SecretKey getSecreteKey(String secretKey) throws Exception {
        byte[] bPasswd = secretKey.getBytes("utf-8");
        byte[] uBPasswd = Arrays.copyOf(bPasswd, 24);
        SecretKey key = new SecretKeySpec(uBPasswd, "DESede");
        return key;
    }
}