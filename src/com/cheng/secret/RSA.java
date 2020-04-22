package com.cheng.secret;

import com.cheng.secret.ui.*;

import javax.swing.*;

public class RSA {

    public static final String KEY_SAVE_PATH = String.valueOf(System.getProperty("user.dir")) + System.getProperty("file.separator") + "RSA秘钥" + System.getProperty("file.separator");

    public RSA() {

    }

    public JPanel content;
    private JPanel createPanel;
    private JPanel encrypt;
    private JPanel decrypt;
    private JPanel sign;
    private JPanel verify;

    private void createUIComponents() {
        GeneratorRSAKeyPair rsaKeyPair = new GeneratorRSAKeyPair();
        createPanel = rsaKeyPair.getContent();

        EncryptForm encryptForm = new EncryptForm();
        encrypt = encryptForm.getContent();

        DecryptForm decryptForm = new DecryptForm();
        decrypt = decryptForm.getContent();

        SignForm signForm = new SignForm();
        sign = signForm.getPanel();

        VerifyForm verifyForm = new VerifyForm();
        verify = verifyForm.getPanel();
    }
}
