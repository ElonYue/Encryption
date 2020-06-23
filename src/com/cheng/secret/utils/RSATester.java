package com.cheng.secret.utils;

import java.security.Security;
import java.util.Map;


public class RSATester {

    static String publicKey;
    static String privateKey;

    public static final String KEY_SAVE_PATH = String.valueOf(System.getProperty("user.dir")) + System.getProperty("file.separator") + "RSA" + System.getProperty("file.separator");



    public static void main(String[] args) throws Exception {
        System.out.println(KEY_SAVE_PATH);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//        try {
//            Map<String, Object> keyMap = RSAUtils.genKeyPair();
//            publicKey = RSAUtils.getPublicKey(keyMap);
//            privateKey = RSAUtils.getPrivateKey(keyMap);
//            System.out.println("公钥: \n\r" + publicKey);
//            System.out.println("私钥： \n\r" + privateKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        RSAUtils.generateKeyPair("PublicKey.pem", "PrivateKey.pem");
//        publicKey = RSAUtils.getKeyFromFile("PublicKey.pem");
//        privateKey = RSAUtils.getKeyFromFile("PrivateKey.pem");
//        System.out.println("公钥: \n\r" + publicKey);
//        System.out.println("私钥： \n\r" + privateKey);

        String[] keys = {"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQAwCkOKa8qetpDMFoWkFfaZ4ZkBSVlLIZ5d+VgmnJ3Tdt9WB9KQy3DpPN4XWiwdBF6mki6HyhprosV+V5xEmgNO6A+pV6XBlbKGBd+ZSoBysY4P+j4f9lQV+JuoIwGK/FM1Yifa42cCF+IyWEdBlI161tK3tuYn40yvDxAI+jQQIDAQAB","MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJADAKQ4pryp62kMwWhaQV9pnhmQFJWUshnl35WCacndN231YH0pDLcOk83hdaLB0EXqaSLofKGmuixX5XnESaA07oD6lXpcGVsoYF35lKgHKxjg/6Ph/2VBX4m6gjAYr8UzViJ9rjZwIX4jJYR0GUjXrW0re25ifjTK8PEAj6NBAgMBAAECgYBN/9JRH/3vIXlHpIj8pCXUL7hZ0BfWDHuKW18F82So2RA0ljp0j887NsIHOJ4mUSMlOxlGtvUJ+OvNKK45jX7yXLSXItdzeuFIB4sUAcFEJB60CEpxnDpXwuVzWKMNbHPi4huhiNBftkrPo7XG7MgHvyhiro0+HQvrhBnUI/a3gQJBANy6nmuYr7hvvNBXVhry0aCchV2L2Lmh/Qkul677bEcCGtg+szZhS34isOEuRtBrpqqXKPTja7iYChWzMTG2F+UCQQCnBhqWCWGsHBFTzQ7udfIOYD6mI5Tf7VTadEpG6GbQzbEe7LaAds09u5mhPderw2kBLcsdnJetdixwUBhUoLAtAkAyuAL88Em4VJyLNvZHHPKEOFMAysiS5yDwgi9Ceu1HZ7lpZQbjAPpK0ofYRjzpWUMjwbYch0FSWkU+9TzhkljtAkAauVlLpFiiZyNGm0rGUMzYDAI6JvtNEPZAtdaknc7FngO3BPz2dQvF0txz9icp7mxAqgkTxdhnAr99gPNDpypJAkBABpw/4Y7EnB3j835ytqzmuJeSk/4vTTNeWiPYvyiwNPiybuLr8KGHTprx/x9NgdagSHHiU58SiKiI7fBzQh+P"};

        publicKey = keys[0];
        privateKey = keys[1];

//        testDes();
        test();
        test1();
//        testSign();

    }

//    static void testDes() {
//        //待加密内容
//        String str = "张三";
//        String result = DesCrypt.encrypt(str, DesCrypt.KEY);
//        System.out.println("加密后：" + new String(result));
//        //直接将如上内容解密
//        try {
//            byte[] decryResult = DesCrypt.decrypt(result, DesCrypt.KEY);
//            System.out.println("解密后：" + new String(decryResult));
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//    }

    static void test() throws Exception {
        System.out.println("==========test===========");
        System.out.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("加密前文字：\r\n" + source);
        String encodedData = RSAUtils.encryptByPublicKey(source, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        String target = RSAUtils.decryptByPrivateKey(encodedData,  privateKey);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void test1() throws Exception {
        System.out.println("==========test1===========");
        System.out.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义";
        System.out.println("原文字：\r\n" + source);
        String encodedData = RSAUtils.encryptByPrivateKey(source, privateKey);
        System.out.println("加密后：\r\n" + encodedData);
        String target = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        System.out.println("解密后: \r\n" + target);

    }

    static void testSign() throws Exception {
        System.out.println("=========testSign============");
        System.out.println("私钥签名——公钥验证签名");
        String source = "如果过去不去，如果未来不来，如果现在还在";
        String sign = RSAUtils.sign(source, privateKey);
        System.out.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(source, sign, publicKey);
        System.out.println("验证结果:\r" + status);
    }

}
