package com.cheng.secret.utils;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全<br/>
 * java.lang.String 保存明文，byte 数组保存二进制密文
 * </p>
 *
 * @author chengyue
 * @version 1.0
 * @date 2012-4-26
 */
public class RSAUtils {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    public static final String PADDING = "RSA/None/PKCS1Padding";

    /**
     * 指定key的大小
     */
    private static final int KEYSIZE = 1024;

    /**
     * 签名算法SHA1WithRSA
     */
    public static final String RSA_SHA1 = "SHA1WithRSA";
    public static final String RSA_SHA256 = "SHA256withRSA";
    public static final String RSA_MD5 = "MD5withRSA";

    /**
     * 签名算法
     * SHA1WithRSA
     * SHA256withRSA
     * MD5withRSA
     */
    public static final String SIGNATURE_ALGORITHM = RSA_SHA1;

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static Map<String, Object> genKeyPair() throws Exception {
        return genKeyPair(KEYSIZE);
    }

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return 密钥对
     * @throws Exception Exception
     */
    public static Map<String, Object> genKeyPair(int keySize) throws Exception {
        // RSA算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 利用上面的随机数据源初始化这个KeyPairGenerator对象
        keyPairGen.initialize(keySize);
        // 生成密匙对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 生成密钥对 保存到相关目录
     *
     * @param privateKeyPath 私钥存储路径
     * @param publicKeyPath  公钥存储路径
     */
    public static void generateKeyPair(String publicKeyPath,
                                       String privateKeyPath) throws Exception {
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /** 生成密匙对 */
        KeyPair keyPair = kpg.generateKeyPair();
        /** 得到公钥 */
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        /** 得到私钥 */
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        /** 用对象流将生成的密钥写入文件 */
        FileOutputStream os1 = new FileOutputStream(publicKeyPath);
        FileOutputStream os2 = new FileOutputStream(privateKeyPath);
        os1.write(encode(publicKey.getEncoded()).getBytes());
        os2.write(encode(privateKey.getEncoded()).getBytes());
        /** 清空缓存，关闭文件输出流 */
        os1.close();
        os2.close();
    }

    /**
     * getKeyFromFile: 将文件中的公钥对象读出<br/>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     * @author chengyue
     * @since JDK 1.6
     */
    public static String getKeyFromFile(String path) {
        FileInputStream inputStream = null;
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            inputStream = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\r");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        return sb.toString();
    }

    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    private static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       进行数字签名的源数据
     * @param privateKey 私钥(BASE64编码)
     * @return 数字签名(BASE64编码的字符串)
     * @throws Exception
     */
    public static String sign(String data, String privateKey) throws Exception {
        PrivateKey privateK = getPrivateKey(privateKey);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return encode(signature.sign());
    }

    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      进行数字签名的源数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名(BASE64编码的字符串)
     * @return
     * @throws Exception
     */
    public static boolean verify(String data, String sign, String publicKey) throws Exception {
        PublicKey publicK = getPublicKey(publicKey);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return signature.verify(decode(sign));
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encrypted 已加密数据((BASE64编码))
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String encrypted, String privateKey)
            throws Exception {
        byte[] encryptedData = decode(encrypted);

        PrivateKey privateK = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int inputLen = encryptedData.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedStr 已加密数据(BASE64编码)
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(String encryptedStr,
                                            String publicKey) throws Exception {
        byte[] encryptedData = decode(encryptedStr);
        Key publicK = getPublicKey(publicKey);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher
                        .doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher
                        .doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param source      明文源数据
     * @param publicKey 公钥(BASE64编码)
     * @return 密文(BASE64编码)
     * @throws Exception
     */
    public static String encryptByPublicKey(String source, String publicKey)
            throws Exception {
        byte[] data = source.getBytes(StandardCharsets.UTF_8);
        Key publicK = getPublicKey(publicKey);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encode(encryptedData);
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param source     源数据
     * @param privateKey 私钥(BASE64编码)
     * @return 密文(BASE64编码)
     * @throws Exception
     */
    public static String encryptByPrivateKey(String source, String privateKey)
            throws Exception {
        byte[] data = source.getBytes(StandardCharsets.UTF_8);
        Key privateK = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encode(encryptedData);
    }

    /**
     * 获取私钥
     *
     * @param keyMap 密钥对
     * @return 私钥字符串
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encode(key.getEncoded());
    }

    /**
     * 获取公钥
     *
     * @param keyMap 密钥对
     * @return 公钥字符串
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encode(key.getEncoded());
    }

    /**
     * BASE64字符串解码为二进制数据
     *
     * @param base64
     * @return
     * @throws Exception
     */
    private static byte[] decode(String base64) throws Exception {
        String source = base64.replace("\n", "").replace("\r", "");
        return java.util.Base64.getDecoder().decode(source);
//        return new BASE64Decoder().decodeBuffer(base64);
//        return org.apache.commons.codec.binary.Base64.decodeBase64(base64.replace("\r\n", ""));
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    private static String encode(byte[] bytes) throws Exception {
        String dest = java.util.Base64.getEncoder().encodeToString(bytes);
        return dest.replace("\n", "").replace("\r", "");
//        return new BASE64Encoder().encode(bytes);
//        return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes).replace("\r\n", "");
    }

}