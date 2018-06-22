package com.cf.blog.design.observer.jdk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFrameTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JButton button = new JButton("展示");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("展示!");
            }
        });

        frame.getContentPane().add(BorderLayout.CENTER, button);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
