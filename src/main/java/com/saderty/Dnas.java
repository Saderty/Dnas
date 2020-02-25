package com.saderty;

import com.saderty.Functions.Base;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

        JCheckBox checkBox1 = new JCheckBox();
        checkBox1.setBounds(340, 40, 40, 40);
        checkBox1.setText("1");
        add(checkBox1);
        JCheckBox checkBox2 = new JCheckBox();
        checkBox2.setBounds(340, 90, 40, 40);
        checkBox2.setText("2");
        add(checkBox2);
        JCheckBox checkBox3 = new JCheckBox();
        checkBox3.setBounds(340, 140, 40, 40);
        checkBox3.setText("3");
        add(checkBox3);
        JCheckBox checkBox4 = new JCheckBox();
        checkBox4.setBounds(340, 190, 40, 40);
        checkBox4.setText("4");
        add(checkBox4);
        JCheckBox checkBox5 = new JCheckBox();
        checkBox5.setBounds(340, 240, 40, 40);
        checkBox5.setText("5");
        add(checkBox5);

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

        button1.addActionListener(e -> {
            Base base = new Base(textArea);
            //base.processFunctions4(textArea1);
            if (checkBox1.isSelected()) base.processFunctions1(textArea1);
            if (checkBox2.isSelected()) base.processFunctions2(textArea1);
            if (checkBox3.isSelected()) base.processFunctions3(textArea1);
            if (checkBox4.isSelected()) base.processFunctions4(textArea1);
            if (checkBox5.isSelected()) base.processFunctions4(textArea1);
        });
        checkBox1.addActionListener(actionEvent -> {
            checkBox2.setSelected(false);
            checkBox3.setSelected(false);
            checkBox4.setSelected(false);
            checkBox5.setSelected(false);
            textArea.setText("N=\nn()=\nt=\nt[]=");
        });
        checkBox2.addActionListener(actionEvent -> {
            checkBox1.setSelected(false);
            checkBox3.setSelected(false);
            checkBox4.setSelected(false);
            checkBox5.setSelected(false);
            textArea.setText("l=\nt=\nm=\na=\nk=");
        });
        checkBox3.addActionListener(actionEvent -> {
            checkBox2.setSelected(false);
            checkBox1.setSelected(false);
            checkBox4.setSelected(false);
            checkBox5.setSelected(false);
            textArea.setText("l=\nl[]=\nn=\nt=\np=\np[]=\np_c");
        });
        checkBox4.addActionListener(actionEvent -> {
            checkBox2.setSelected(false);
            checkBox3.setSelected(false);
            checkBox1.setSelected(false);
            checkBox5.setSelected(false);
            textArea.setText("n=\nm=\nm_i\nt=\nl=\nl0=\nl1=");
        });
        checkBox5.addActionListener(actionEvent -> {
            checkBox2.setSelected(false);
            checkBox3.setSelected(false);
            checkBox4.setSelected(false);
            checkBox1.setSelected(false);
            textArea.setText("n=\nm=\nm_i\nt=\nl=\nl0=\nl1=");
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Dnas();
    }
}
