package com.cheng.secret.ui;

import com.cheng.secret.utils.RSAUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EncryptForm {


    public EncryptForm() {
        btnEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = sourceTextArea.getText();
                String key = keyTextArea.getText();
                try {
                    String result = RSAUtils.encryptByPublicKey(source, key);
                    resultTextArea.setText(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    resultTextArea.setText(ex.getMessage());
                }
            }
        });
        btnSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = sourceTextArea.getText();
                String key = keyTextArea.getText();
                try {
                    String result = RSAUtils.encryptByPrivateKey(source, key);
                    resultTextArea.setText(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    resultTextArea.setText(ex.getMessage());
                }
            }
        });
    }

    private JPanel content;
    private JTextArea sourceTextArea;
    private JTextArea resultTextArea;
    private JTextArea keyTextArea;
    private JButton btnEncrypt;
    private JButton btnSign;

    public JPanel getContent() {
        return content;
    }
}
