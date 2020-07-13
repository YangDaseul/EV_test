package com.genesis.apps.comm.util.crypt;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ajc on 2018-01-02.
 */

public class CryptUtils {
    public static byte[] sha256(byte[]... data) {
        byte[] SHA = null;
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256", "BC");
            for (byte[] d : data) {
                sh.update(d);
            }
            SHA = sh.digest();
        } catch (Exception e) {
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
    }

    public static byte[] encrypt(byte[] key, byte[] iv, byte[] data) {
        if (key.length != 16) return null;

        byte[] encrypted = null;

        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            c.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            encrypted = c.doFinal(data);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return encrypted;
    }

    public static byte[] decrypt(byte[] key, byte[] iv, byte[] data) {
        if (key.length != 16) return null;

        byte[] encrypted = null;

        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            c.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            encrypted = c.doFinal(data);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return encrypted;
    }

    public static byte[] hmac_sha256(byte[] key, byte[]... data) {
        byte[] hash = null;

        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256", "BC");
            SecretKeySpec keySpec = new SecretKeySpec(key, "HmacSHA256");
            sha256_HMAC.init(keySpec);
            for (byte[] d: data) {
                sha256_HMAC.update(d);
            }
            hash = sha256_HMAC.doFinal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }
}
