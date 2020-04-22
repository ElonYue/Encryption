package com.cheng.secret.ui;


import com.cheng.secret.utils.RSAUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

public class GeneratorRSAKeyPair {

    static String publicKey;
    static String privateKey;

    public static final String KEY_SAVE_PATH = String.valueOf(System.getProperty("user.dir")) + System.getProperty("file.separator") + "RSA秘钥" + System.getProperty("file.separator");

    public GeneratorRSAKeyPair() {
        btnCreateKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Map<String, Object> keyMap = RSAUtils.genKeyPair();
                    publicKey = RSAUtils.getPublicKey(keyMap);
                    privateKey = RSAUtils.getPrivateKey(keyMap);
                    publicKeyText.setText(publicKey);
                    privateKeyText.setText(privateKey);
                    System.out.println("公钥: \n\r" + publicKey);
                    System.out.println("私钥： \n\r" + privateKey);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    public JPanel content;
    private JTextArea privateKeyText;
    private JTextArea publicKeyText;
    private JButton btnCreateKey;

    private JRadioButton a2048RadioButton;
    private JRadioButton a1024RadioButton;

    public JPanel getContent() {
        return content;
    }
}
