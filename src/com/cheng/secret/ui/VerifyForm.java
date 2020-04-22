package com.cheng.secret.ui;

import com.cheng.secret.utils.RSAUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerifyForm {
    private JPanel panel;
    private JTextArea keyTextArea;
    private JTextArea sourceTextArea;
    private JTextArea signTextArea;
    private JTextField resultTextField;
    private JButton btnVerify;

    public VerifyForm() {
        btnVerify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String key = keyTextArea.getText();
                    String source = sourceTextArea.getText();
                    String sign = signTextArea.getText();
                    boolean result = RSAUtils.verify(source, sign, key);
                    resultTextField.setText(result ? "成功" : "失败");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    resultTextField.setText(ex.getMessage());
                }

            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
