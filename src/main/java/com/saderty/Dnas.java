package com.saderty;

import com.saderty.Functions.Base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dnas extends JFrame {
    Dnas() {
        setTitle("ДНАС");
        getContentPane().setBackground(new Color(150, 150, 255));
        setSize(1000, 1000);
        setLayout(null);

        Font f = getContentPane().getFont();

        final JTextArea textArea = new JTextArea();
        textArea.setBounds(10, 10, 300, 600);
        textArea.setBackground(new Color(255, 255, 200));
        textArea.setFont(new Font(f.getFontName(), f.getStyle(), f.getSize() + 2));
        add(textArea);

        final JTextArea textArea1 = new JTextArea();
        textArea1.setBounds(410, 10, 300, 490);
        textArea1.setBackground(new Color(255, 255, 200));
        textArea1.setFont(new Font(f.getFontName(), f.getStyle(), f.getSize() + 2));
        add(textArea1);

        JButton button1 = new JButton();
        button1.setBounds(410, 510, 300, 100);
        button1.setFont(new Font(f.getFontName(), f.getStyle(), f.getSize() + 2));
        button1.setText("Рассчитать");
        add(button1);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Base base = new Base(textArea);
                base.processFunctions2(textArea1);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Dnas();
    }
}
