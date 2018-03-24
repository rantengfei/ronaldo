package aibili.ronaldo.utils;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.SecureRandom;

/**
 * Created by rtf on  2018/1/28.
 */
public class CipherUtil {
    /**
     * encrypt base on Base64
     */
    public static String encryptByBase64(byte[] encrypt) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(encrypt);

    }

    /**
     * decrypt base on Base64
     */
    public static byte[] decryptByBase64(String decrypt) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(decrypt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * encrypt base on DES or 3DES or AES.
     */
    public static String encrypt(String encrypt, String algorithm, String key) {
        try {
            byte[] targetToByte = encrypt.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), algorithm);
            cipher.init(Cipher.ENCRYPT_MODE,securekey);
            byte[] result = cipher.doFinal(targetToByte);
            return encryptByBase64(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * decrypt base on DES or 3DES or AES.
     */
    public static String decrypt(String decrypt, String algorithm, String key) {
        try {
            byte[] targetToByte = decryptByBase64(decrypt);
            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), algorithm);
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            byte[] result = cipher.doFinal(targetToByte);
            return new String(result, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * URLEncoder encode.
     */
    public static String urlEncoder(String encrypt){
        try {
            encrypt = URLEncoder.encode(encrypt, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encrypt;
    }


    /**
     * URLDecoder decode.
     */
    public static String urlDecoder(String decrypt){
        try {
            decrypt = URLDecoder.decode(decrypt, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decrypt;
    }

    public static void main(String[] args) {
        String target = "{'cid': '1111', 'ckey': '88ba5256952549c68cd0f6d64669f645', 'page_num': 1, 'page_size': 10}";
//        try {
//            target = URLEncoder.encode(target, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String aesKey = "De8sM4FZvRCRia4V5wJudw==";
        //DES
//        String encrypt = encrypt(target, "DES", "ronaldo");
//        System.out.println("加密后：" + encrypt);
//        String decrypt = decrypt(encrypt, "DES", "ronaldo");
//        System.out.println("解密后：" + decrypt);

        //3DES
//        String encrypt = encrypt(target, "DESede", "ronaldo");
//        System.out.println("加密后：" + encrypt);
//        String decrypt = decrypt(encrypt, "DESede", "im a key");
//        System.out.println("解密后：" + decrypt);

         //AES
         String encrypt = encrypt(urlEncoder(target), "AES", aesKey);
         System.out.println("加密后：" + encrypt);
         String decrypt = decrypt(encrypt, "AES", aesKey);
         System.out.println("解密后：" + urlDecoder(decrypt));

    }
}
