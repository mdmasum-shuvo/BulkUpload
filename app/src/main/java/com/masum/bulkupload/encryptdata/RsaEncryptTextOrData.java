package com.masum.bulkupload.encryptdata;//package com.tds;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RsaEncryptTextOrData {

    final private static String string_public_key =
            "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAotuJuj/svkA6ilz3UyqF\n" +
                    "1gCGXqPGqlw3rvYkjoiqw+4NmosFlMqsZR+pXbHYEIzys72fNyJyW67D8dKzBONB\n" +
                    "r/Cwwtog2O4n5qMGR8v7hc/dLj9Y6PqDUirm8v/UhuaPhCI+qlcsDEiqJuslUUff\n" +
                    "VBARIE7EXZ0KKbkM1BLpM0bZIH+v3QnaPoppwYZz2usSjTasOFo2sSyXUOWGuTpT\n" +
                    "2lK0NUHlMIRookPgGAalRbQeEnblGzVpCiPctNUe+l9BBOtjKRPBN+hDoWgZw046\n" +
                    "PSzDOAfdGcvCkAyNVXJyFrzmjoPiY/lCb1pMh0SSWEmmsKM5pOfXByDsnmGZIjIN\n" +
                    "l14XNOCsZkcnqOF7Uth4Oz9l623UmIWhvoG+zFczUISRJiiGIpQPuyncriOL+IIG\n" +
                    "XkkTATv2o/NJZ3Ayd96ykU64UJqeeIZggK8suf6b1OgnMa/X7vxnU8SeM3WD9xCm\n" +
                    "eQG6T2Ky+qmWvdv3ke8dbUHMG9Dxo90ODjm1HnLrkgsod2UT0AGxAbuK5FKNISfL\n" +
                    "2JhtmsxnESbs/+OtipjYcFsJ/DdASuSJRB820epdVBUlAchZ5nklqA7A/p6Qpazv\n" +
                    "wGYhUuQULTJ0dyWNnT4Kb18uaxeODdXA1sb0bNiLT3s2ef3CaCf6BXCbtlfSvK0T\n" +
                    "P6ApCVbnWK5wKmMrZmxkuPcCAwEAAQ==\n";

    static String encryptToBase64(byte[] data) throws Exception {
        return doEncrypt(data);
    }

    public static String encryptToBase64(String data) throws Exception {
        return doEncrypt(data.getBytes("UTF-8"));
    }

    private static String doEncrypt(byte[] data) throws Exception {
        PublicKey apiPublicKey = getRSAPublicKeyFromString();
//        Cipher rsaCipher        = Cipher.getInstance("RSA/None/PKCS1Padding");
        //Cipher rsaCipher        = Cipher.getInstance("RSA/ECB/NoPadding");
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, apiPublicKey);
        return Base64.encodeToString(rsaCipher.doFinal(data), Base64.NO_WRAP);
    }

    private static PublicKey getRSAPublicKeyFromString() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] publicKeyBytes = Base64.decode(string_public_key.getBytes("UTF-8"), Base64.DEFAULT);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(spec);
    }
}
