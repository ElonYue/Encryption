package com.cheng.secret;

import com.apple.eawt.Application;

import javax.swing.*;
import java.awt.*;
import java.security.Security;

public class Main {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    static {
        //获得操作系统
        String OsName = System.getProperty("os.name");
        //是mac 就设置dock图标
        if (OsName.contains("Mac")) {
            Image icon_image = new ImageIcon(Main.class.getResource("/img/logo.png")).getImage();
            //指定mac 的dock图标
            Application app = Application.getApplication();
            app.setDockIconImage(icon_image);
        }
    }


    public static void main(String[] args) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        init(WIDTH, HEIGHT);
    }

    public static void init(int width, int height) {
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setTitle("RSA");
        frame.setContentPane(new RSA().content);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
        frame.setVisible(true);
//        new Image(Main.class.getResource("/img/logo.png"));
        frame.setIconImage(new ImageIcon(Main.class.getResource("/img/logo.png")).getImage());
    }
}
