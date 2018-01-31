package aibili.ronaldo.utils;

import java.security.MessageDigest;

/**
 * Created by rtf on  2018/1/28.
 */
public class UploadFile {
    private static final String SALT = "ronaldo";

    public static String encode(String password) {
        password = password + SALT;
        return processEncode(password);
    }

    public static String decode(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    public static String processEncode(String password) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    public static void main(String[] args) {
        String s = new String("123456");
        System.out.println("原始：" + s);
        System.out.println("MD5编码：" + UploadFile.encode(s));
        System.out.println("MD5解码：" + UploadFile.decode(UploadFile.decode(s)));

    }
}
