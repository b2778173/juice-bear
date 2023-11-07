package com.juice.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * Created by ken on 2018/7/12.
 */

@Slf4j
public class DESEncryptUtils {

    private Cipher cipher;

    public static String encrypt(String plainText, String key)  {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            byte[] encryptedData = cipher.doFinal(plainText.getBytes());
            return Base64.encodeBase64String(encryptedData).replaceAll("\\s*", "");
        } catch (Exception e) {

        }
        return null;
    }

    public static String decrypt(String input, String key) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
            byte[] result = cipher.doFinal(Base64.decodeBase64(input));
            return new String(result);
        } catch (Exception e) {
            log.warn("[decrypt failed] input = {}, error msg = {} ", input, e);
        }
        return input;
    }

    public DESEncryptUtils(String key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
    }

    public String encrypt(String plainText) throws Exception {
        byte[] encryptedData = cipher.doFinal(plainText.getBytes());
        return Base64.encodeBase64String(encryptedData).replaceAll("\\s*", "");
    }

    /**
     * LB彩票用(DESede 加密)
     * @param str
     * @param desKey
     * @return
     * @throws Exception
     */
    public static String encryptDes(String str, String desKey) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance("MD5");

        // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
        byte[] md5key = md5.digest(desKey.getBytes());

        Integer size = md5key.length;
        //System.out.println(size);

        /**
         * 若key长度为16位，则进行重组为24位
         * 此3DES加密只支持24位key
         */
        if (md5key.length == 16) {
            byte[] tmpKey = new byte[24];
            System.arraycopy(md5key, 0, tmpKey, 0, 16);
            System.arraycopy(md5key, 0, tmpKey, 16, 8);
            md5key = tmpKey;
        }

        SecretKey key = new SecretKeySpec(md5key, "DESede");

        Cipher ecipher;
        ecipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        ecipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] data = str.getBytes("UTF-8");

        byte[] encryptedArray = ecipher.doFinal(data);

        String dd = new Base64().encodeToString(encryptedArray);

        //System.out.println(dd);

        return dd;

    }



}
