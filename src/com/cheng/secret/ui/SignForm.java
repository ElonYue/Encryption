package com.cheng.secret.ui;

import com.cheng.secret.utils.RSAUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignForm {
    private JPanel panel;
    private JTextArea keyTextArea;
    private JTextArea sourceTextArea;
    private JTextArea resultTextArea;
    private JButton btnSign;

    public SignForm() {
        btnSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String key = keyTextArea.getText();
                    String source = sourceTextArea.getText();
                    String result = RSAUtils.sign(source, key);
                    resultTextArea.setText(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    resultTextArea.setText(ex.getMessage());
                }

            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
