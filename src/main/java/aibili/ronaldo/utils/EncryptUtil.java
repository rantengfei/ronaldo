package aibili.ronaldo.utils;

import com.sun.deploy.net.URLEncoder;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Created by rtf on  2018/3/24.
 */
public class EncryptUtil {
    public static String encryptByAESAndBase64(String base64EncodedAESKey, String dataStr) {
        SecretKey secretKey;
        secretKey = restoreAESKey(base64EncodedAESKey);

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, secretKey);
            String encryptedDataStr = Base64.encodeBase64String(cipher.doFinal(dataStr.getBytes()));
            return encryptedDataStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptByAESAndBase64(String base64EncodedAESKey, String encryptedDataStr) {
        SecretKey secretKey = restoreAESKey(base64EncodedAESKey);
        try {
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(2, secretKey);

            String decryptedDataStr = new String(cipher.doFinal(Base64.decodeBase64(encryptedDataStr)));

            return decryptedDataStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SecretKey restoreAESKey(String base64EncodedAESKey){
        byte[] keyByteArray = Base64.decodeBase64(base64EncodedAESKey);

        SecretKey secretKey = new SecretKeySpec(keyByteArray, "AES");

        return secretKey;
    }
    public static String escapeSymbol(String content) {
        if ((content.indexOf("+") > 0) || (content.indexOf("/") > 0)) {
            content = content.replaceAll("\\+", ".");
            content = content.replaceAll("\\/", "_");
        }
        return content;
    }
    public static String restoreSymbol(String content) {
        if ((content.indexOf(".") > 0) || (content.indexOf("_") > 0)) {
            content = content.replaceAll("\\.", "+");
            content = content.replaceAll("\\_", "/");
        }
        return content;
    }
    public static void main(String[] args) {
        String aesKey = "De8sM4FZvRCRia4V5wJudw==";
        String target = "{'cid': 'huanqiujinrong', 'ckey': 'huanqiujinrong', 'phonenum': '18152149829','target':'product_detail', 'product_code':'04757f50d2e54845b02b890392e287b9'}";
        try {
            target = URLEncoder.encode(target, "UTF-8");
            System.out.println("tatget:" + target);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String aesEncryptedStr1 = encryptByAESAndBase64(aesKey, target);
        System.out.println("aesEncryptedStr1：" + aesEncryptedStr1);
        aesEncryptedStr1 = escapeSymbol(aesEncryptedStr1);
        System.out.println("加密后：" + aesEncryptedStr1);



        aesEncryptedStr1 = restoreSymbol(aesEncryptedStr1);
        String aesDecryptedStr1 = EncryptUtil.decryptByAESAndBase64(aesKey, aesEncryptedStr1);
        try {
            aesDecryptedStr1 = URLDecoder.decode(aesDecryptedStr1, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("解密后：" + aesDecryptedStr1);
    }
}
