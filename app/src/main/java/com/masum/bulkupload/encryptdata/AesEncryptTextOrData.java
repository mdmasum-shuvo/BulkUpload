package com.masum.bulkupload.encryptdata;//package com.tds;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AesEncryptTextOrData {
    static AesEncryptedData encryptToBase64(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return doEncrypt(data);
    }

    public static AesEncryptedData encryptToBase64(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return doEncrypt(data.getBytes());
    }

    private static AesEncryptedData doEncrypt(byte[] plainTextInByteArray) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //Generate Random KEY
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);
        SecretKey secretKey = generator.generateKey();

        //Generate Random IV (initialization Vector)
        byte[] iv = new byte[16]; //16 bit for AES-256-CBC Encryption/Decryption
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv);

        //Get Cipher Algorithm Instance
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //Initialize Cipher with Encryption-Key And IV(initialization vector)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParamSpec);
        //Encrypt the Plain Text
        byte[] encryptedData = cipher.doFinal(plainTextInByteArray);

        //Build Result Class Properties
        AesEncryptedData res        = new AesEncryptedData();
        res.SecretKeyInBase64       = Base64.encodeToString(secretKey.getEncoded(), Base64.NO_WRAP);
        res.IvInBase64              = Base64.encodeToString(iv, Base64.NO_WRAP);
        res.EncryptedDataInBase64   = Base64.encodeToString(encryptedData, Base64.NO_WRAP);

        return res;
    }
}
