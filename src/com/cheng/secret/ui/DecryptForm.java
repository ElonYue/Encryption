package com.cheng.secret.ui;

import com.cheng.secret.utils.RSAUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecryptForm {

    public DecryptForm() {
        // 私钥解密
        btnDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = sourceTextArea.getText();
                String key = keyTextArea.getText();
                try {
                    byte[] result = RSAUtils.decryptByPrivateKey(source, key);
                    resultTextArea.setText(new String(result));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        // 验签(公钥解密)
        btnVerify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = sourceTextArea.getText();
                String key = keyTextArea.getText();
                try {
                    byte[] result = RSAUtils.decryptByPublicKey(source, key);
                    resultTextArea.setText(new String(result));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    private JPanel content;
    private JTextArea sourceTextArea;
    private JTextArea resultTextArea;
    private JTextArea keyTextArea;
    private JButton btnDecrypt;
    private JButton btnVerify;

    public JPanel getContent() {
        return content;
    }
}
