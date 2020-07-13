package com.genesis.apps.comm.util.crypt;

import android.util.Log;

import org.spongycastle.crypto.paddings.ISO7816d4Padding;

import java.security.SecureRandom;

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

    public static byte[] decryptAES128_CBC(byte[] key, byte[] icv, byte[] plain) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/noPadding", "BC");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivspec = new IvParameterSpec(icv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivspec);
        return cipher.doFinal(plain);
    }

    public static byte[] encryptAES128_CBC(byte[] key, byte[] icv, byte[] plain) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/noPadding", "BC");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivspec = new IvParameterSpec(icv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);
        return cipher.doFinal(plain);
    }

    public static byte[] encryptAES128_CBC_7816Padding(byte[] key, byte[] icv, byte[] plain) throws Exception {
        int blockSize = 16;
        boolean need = plain.length % blockSize == 0 ? false : true;
        byte[] padded = null;
        if (need) {
            int padSize = blockSize - (plain.length % blockSize);
            ISO7816d4Padding padding = new ISO7816d4Padding();
            padding.init(new SecureRandom());
            padded = ByteUtils.append(plain, new byte[padSize]);
            padding.addPadding(padded, plain.length);
        } else {
            padded = plain;
        }
        Log.d("TEST", "encryptAES128_CBC_7816Padding : " + HexStringConverter.hexToString(padded));

        Cipher cipher = Cipher.getInstance("AES/CBC/noPadding", "BC");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivspec = new IvParameterSpec(icv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);
        return cipher.doFinal(padded);
    }

}
