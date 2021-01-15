package com.genesis.apps.comm.util.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ajc on 2018-03-29.
 */

public class AesUtils {
    public static byte[] decryptAES128_CTR(byte[] key, byte[] nonce, byte[] plain) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CTR/noPadding", "BC");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivspec = new IvParameterSpec(nonce);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivspec);
        return cipher.doFinal(plain);
    }

    public static byte[] encryptAES128_CTR(byte[] key, byte[] nonce, byte[] plain) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CTR/noPadding", "BC");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivspec = new IvParameterSpec(nonce);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);
        return cipher.doFinal(plain);
    }
}
