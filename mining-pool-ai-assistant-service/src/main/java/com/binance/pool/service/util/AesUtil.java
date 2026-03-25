package com.binance.pool.service.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by yyh on 2020/4/14
 */
public class AesUtil {
    public static final int AES_KEY_SIZE = 256;
    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;

    public static String encrypt(String text, String pwd) throws Exception {
        byte[] IV = new byte[GCM_IV_LENGTH];

        SecretKey key = getSecretKey(pwd, IV);

        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Encryption
        byte[] cipherText = cipher.doFinal(text.getBytes());

        return byte2hex(cipherText);
    }


    public static String decrypt(String encryptText, String pwd) throws Exception {
        byte[] IV = new byte[GCM_IV_LENGTH];

        SecretKey key = getSecretKey(pwd, IV);
        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Decryption
        byte[] decryptBytes = cipher.doFinal(hex2byte(encryptText.getBytes()));

        return new String(decryptBytes);
    }

    private static SecretKey getSecretKey(String pwd, byte[] IV) throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(pwd.getBytes());
        random.nextBytes(IV);
        keygen.init(AES_KEY_SIZE, random);
        return keygen.generateKey();
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        String pwd = "1231asdfaasdfasdhwyeua!!";
        long start = System.currentTimeMillis();
        String plainText = "efsgnus";

        for (int i = 0; i < 10000; i++) {
            String enStr = encrypt(plainText + i, pwd);
            System.out.println("加密结果：" + enStr);

            String deStr = decrypt(enStr, pwd);
            System.out.println("解密结果：" + deStr);
        }
        System.out.println("spent " + (System.currentTimeMillis() - start) + "ms.");
        String enStr = encrypt(plainText, pwd);
        System.out.println("加密结果：" + enStr);

        String deStr = decrypt(enStr, pwd);
        System.out.println("解密结果：" + deStr);
    }
}
