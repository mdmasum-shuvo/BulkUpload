package com.masum.bulkupload.encryptdata;

import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.masum.bulkupload.AppConstant.ANDROID_ID;
import static com.masum.bulkupload.AppConstant.DEVICE_SERIAL_NO;


//package com.tds;
public class MainEncrypt {
    private static final String TAG = "MainEncrypt";

    public static void main(String[] args) throws Exception {
        //CHECK OLD APP FOR GENERATING THIS JSON-STRING FROM OBJECT(s)
        String JsonString = "{" +
                " \"IsoTemplate\" " + ": \"........ISO-TEMPLATE-HERE-IN-BASE64........\"," +
                " \"DeviceSerialNo\" " + ": \"........FINGERPRINT-DEVICE-SERIAL-NUMBER........\"," +
                " \"HddSerialNo\" " + ": \"........HDD-SERIAL-NUMBER........\"," +
                " \"SoftwareVersion\" " + ": \"........SoftwareVersion........\"" +
                "}";

        AesEncryptedData aesEncryptedData = AesEncryptTextOrData.encryptToBase64(JsonString);

        String finalString = RsaEncryptTextOrData.encryptToBase64(aesEncryptedData.SecretKeyInBase64)
                + ":"
                + RsaEncryptTextOrData.encryptToBase64(aesEncryptedData.IvInBase64)
                + ":"
                + aesEncryptedData.EncryptedDataInBase64;

        System.out.println(finalString);

        //SEND THIS "finalString" TO API
    }

    //    public static String finalEncryptedFP (String isoBase64,String deviceSerialNo,String androidId,String softwareVersion) {
    public static String finalEncryptedFP(String isoBase64) {

        Log.d(TAG, "finalEncryptedFP: isobase64: "+isoBase64);
        Log.d(TAG, "finalEncryptedFP: DEVICE_SERIAL_NO: "+DEVICE_SERIAL_NO);
        Log.d(TAG, "finalEncryptedFP: ANDROID_ID: "+ANDROID_ID);

        /*
        * @Param DEVICE_SERIAL = H52161036401
        * @Param ANDROID_ID = cc78d9dcdfcd4940@4512 for static import
        * */

        String JsonString = "{" +
                " \"IsoTemplate\" " + ": \"" + isoBase64 + "\"," +
//                " \"DeviceSerialNo\" " + ": \"" + DEVICE_SERIAL_NO + "\"," +
                " \"DeviceSerialNo\" " + ": \"" + "H52161036401" + "\"," +
                " \"HddSerialNo\" " + ": \"" + ANDROID_ID + "\"," +
                " \"SoftwareVersion\" " + ": \"app.1\"" +
                "}";


//        private final static String software_version = "app.1";

        AesEncryptedData aesEncryptedData = null;
        try {
            aesEncryptedData = AesEncryptTextOrData.encryptToBase64(JsonString);
        } catch (NoSuchPaddingException e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
        } catch (InvalidKeyException e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
        } catch (BadPaddingException e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
        }

        String finalString = null;
        try {
            finalString = RsaEncryptTextOrData.encryptToBase64(aesEncryptedData.SecretKeyInBase64)
                    + "^^"
                    + RsaEncryptTextOrData.encryptToBase64(aesEncryptedData.IvInBase64)
                    + "^^"
                    + aesEncryptedData.EncryptedDataInBase64;
        } catch (Exception e) {
            Log.d(TAG, "onCreate: " + e.getMessage());
        }

        Log.d(TAG, "onCreate: Final String: " + finalString);
        //System.out.println(finalString);
        return finalString;
    }
}
