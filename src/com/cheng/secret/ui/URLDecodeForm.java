package com.cheng.secret.ui;

import com.cheng.secret.utils.RSAUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLDecodeForm {
    private JPanel panel;
    private JTextArea textEncode;
    private JTextArea textDecode;
    private JButton btnEncode;
    private JButton btnDecode;
    private JTextArea textResult;

    public URLDecodeForm() {
        btnEncode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = textEncode.getText();
                if ("".equals(source)) {
                    textResult.setText("请输入待编码内容");
                }
                try {
                    String result = URLEncoder.encode(source, StandardCharsets.UTF_8.name());
                    copy(result);
                    textResult.setText(result);
                } catch (UnsupportedEncodingException exception) {
                    exception.printStackTrace();
                    textResult.setText(exception.getMessage());
                }

            }
        });
        btnDecode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = textDecode.getText();
                if ("".equals(source)) {
                    textResult.setText("请输入待解码内容");
                }
                try {
                    String result = URLDecoder.decode(source, StandardCharsets.UTF_8.name());
                    textResult.setText(result);
                } catch (UnsupportedEncodingException exception) {
                    exception.printStackTrace();
                    textResult.setText(exception.getMessage());
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    private void copy(String text) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(text);
        clip.setContents(tText, null);
    }
}
