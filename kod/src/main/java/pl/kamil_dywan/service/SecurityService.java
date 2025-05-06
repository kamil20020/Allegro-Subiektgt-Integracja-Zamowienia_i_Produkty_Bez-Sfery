package pl.kamil_dywan.service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public interface SecurityService {

    public static String decryptAes(byte[] key, String base64EncryptedValue) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        Base64.Decoder base64Decoder = Base64.getDecoder();

        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] decodedBase64Key = base64Decoder.decode(key);

        SecretKey secretKey = new SecretKeySpec(decodedBase64Key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]);

        aesCipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        byte[] decodedEncryptedValue = base64Decoder.decode(base64EncryptedValue);

        byte[] decryptedValue = aesCipher.doFinal(decodedEncryptedValue);

        return new String(decryptedValue);
    }

    public static byte[] hashSha(String value) throws NoSuchAlgorithmException {

        if(value == null){
            return null;
        }

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        byte[] valueBytes = value.getBytes();

        byte[] gotHash = messageDigest.digest(valueBytes);

        // Convert to hex

        StringBuilder stringBuilder = new StringBuilder();

        for(byte hashValue : gotHash){

            stringBuilder.append(String.format("%02X", hashValue));
        }

        return stringBuilder.toString().toLowerCase().getBytes();
    }
}
