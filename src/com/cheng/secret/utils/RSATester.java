package com.cheng.secret.utils;

import java.util.Map;


public class RSATester {

    static String publicKey;
    static String privateKey;

    public static final String KEY_SAVE_PATH = String.valueOf(System.getProperty("user.dir")) + System.getProperty("file.separator") + "RSA" + System.getProperty("file.separator");


    public static void main(String[] args) throws Exception {
        System.out.println(KEY_SAVE_PATH);

        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            System.out.println("公钥: \n\r" + publicKey);
            System.out.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        RSAUtils.generateKeyPair("PublicKey.pem", "PrivateKey.pem");
//        publicKey = RSAUtils.getKeyFromFile("PublicKey.pem");
//        privateKey = RSAUtils.getKeyFromFile("PrivateKey.pem");
//        System.err.println("公钥: \n\r" + publicKey);
//        System.err.println("私钥： \n\r" + privateKey);

        String[] keys = {"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQAwCkOKa8qetpDMFoWkFfaZ4ZkBSVlLIZ5d+VgmnJ3Tdt9WB9KQy3DpPN4XWiwdBF6mki6HyhprosV+V5xEmgNO6A+pV6XBlbKGBd+ZSoBysY4P+j4f9lQV+JuoIwGK/FM1Yifa42cCF+IyWEdBlI161tK3tuYn40yvDxAI+jQQIDAQAB","MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJADAKQ4pryp62kMwWhaQV9pnhmQFJWUshnl35WCacndN231YH0pDLcOk83hdaLB0EXqaSLofKGmuixX5XnESaA07oD6lXpcGVsoYF35lKgHKxjg/6Ph/2VBX4m6gjAYr8UzViJ9rjZwIX4jJYR0GUjXrW0re25ifjTK8PEAj6NBAgMBAAECgYBN/9JRH/3vIXlHpIj8pCXUL7hZ0BfWDHuKW18F82So2RA0ljp0j887NsIHOJ4mUSMlOxlGtvUJ+OvNKK45jX7yXLSXItdzeuFIB4sUAcFEJB60CEpxnDpXwuVzWKMNbHPi4huhiNBftkrPo7XG7MgHvyhiro0+HQvrhBnUI/a3gQJBANy6nmuYr7hvvNBXVhry0aCchV2L2Lmh/Qkul677bEcCGtg+szZhS34isOEuRtBrpqqXKPTja7iYChWzMTG2F+UCQQCnBhqWCWGsHBFTzQ7udfIOYD6mI5Tf7VTadEpG6GbQzbEe7LaAds09u5mhPderw2kBLcsdnJetdixwUBhUoLAtAkAyuAL88Em4VJyLNvZHHPKEOFMAysiS5yDwgi9Ceu1HZ7lpZQbjAPpK0ofYRjzpWUMjwbYch0FSWkU+9TzhkljtAkAauVlLpFiiZyNGm0rGUMzYDAI6JvtNEPZAtdaknc7FngO3BPz2dQvF0txz9icp7mxAqgkTxdhnAr99gPNDpypJAkBABpw/4Y7EnB3j835ytqzmuJeSk/4vTTNeWiPYvyiwNPiybuLr8KGHTprx/x9NgdagSHHiU58SiKiI7fBzQh+P"};

        publicKey = keys[0];
        privateKey = keys[1];

//        testDes();
//        test();
//        test1();
//        testSign();


        String data = "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";
        byte[] decodedData = RSAUtils.decryptByPrivateKey(data, privateKey);
        String target = new String(decodedData);
        System.out.println(target);
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
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData,  privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void test1() throws Exception {
        System.out.println("==========test1===========");
        System.out.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义";
        System.out.println("原文字：\r\n" + source);
        String encodedData = RSAUtils.encryptByPrivateKey(source, privateKey);
        System.out.println("加密后：\r\n" + encodedData);
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
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
