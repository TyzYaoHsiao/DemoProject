package com.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;

/**
 * aes 加密
 */
@Slf4j
public class EncryptDecryptUtil {

    public final static String MODEL = "AES/CBC/PKCS5Padding";
    public final static String MODEL_NOPADDING = "AES/ECB/NoPadding";

    /**
     * 加密
     *
     * @param model 加密模式
     * @param key   ase key
     * @param iv    aes iv
     * @param mes   內容
     */
    public static String aesEncrypt(String model, String key, String iv, String mes) {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance(model);
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            return Base64.encodeBase64String(cipher.doFinal(mes.getBytes("UTF-8")));
        } catch (Exception e) {
            log.error("aesEncrypt ERROR: ", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param model   加密模式
     * @param key     ase key
     * @param iv      aes iv
     * @param content 加密內容
     */
    public static String aesDecrypt(String model, String key, String iv, String content) {
        SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance(model);
            cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(iv.getBytes()));
            return new String(cipher.doFinal(Base64.decodeBase64(content)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("aesDecrypt ERROR: ", e);
        }
        return null;
    }

    /**
     * 密碼系統解密方式
     *
     * @param model   演算法
     * @param key     解密 key
     * @param content 加密字串
     */
    public static String aesCPVSDecrypt(String model, byte[] key, String content) {
        SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
        try {
            Cipher cipher = Cipher.getInstance(model);
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return new String(cipher.doFinal(Hex.decodeHex(content)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("aesCPVSDecrypt ERROR: ", e);
        }
        return null;
    }
}