package com.test.trainingprogrambackend.util;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {
    private static final String RSA = "RSA";
    /**
     * 从PEM格式的字符串中加载私钥
     * @param privateKeyPEM 私钥字符串
     * @return 私钥对象
     * @throws Exception 异常
     */
    public static PrivateKey loadPrivateKey(String privateKeyPEM) throws Exception {
        byte[] encodedPrivateKey = Base64.getDecoder().decode(privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s", ""));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 使用私钥解密数据
     * @param encryptedData 加密数据
     * @param privateKey 私钥
     * @return 解密后的字符串
     * @throws Exception 异常
     */
    public static String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}

