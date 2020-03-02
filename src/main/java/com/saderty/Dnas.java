package com.saderty;

import com.saderty.Functions.Base;

import javax.swing.*;
import java.awt.*;

import static com.saderty.Functions.Base.getVarHelp;

public class Dnas extends JFrame {
    public static final String lambda = "\u03bb";
    public static final String sigma = "\uD835\uDED4";

    Dnas() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        JTextArea textArea2 = new JTextArea();
        textArea2.setBounds(720, 10, 500, 490);
        textArea2.setBackground(new Color(255, 255, 200));
        textArea2.setFont(new Font(f.getFontName(), f.getStyle(), f.getSize() + 2));
        add(textArea2);

        JButton button1 = new JButton();
        button1.setBounds(410, 510, 300, 100);
        button1.setFont(new Font(f.getFontName(), f.getStyle(), f.getSize() + 2));
        button1.setText("Рассчитать");
        add(button1);

        button1.addActionListener(e -> {
            Base base = new Base(textArea);
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
            textArea.setText("N=\nn(t)=\nt=\nt[]=");
            textArea2.setText(getVarHelp("N", "n(t)", "P(t)", "q(t)", "m(t)", "f(t)", lambda + "(t)"));
        });
        checkBox2.addActionListener(actionEvent -> {
            checkBox1.setSelected(false);
            checkBox3.setSelected(false);
            checkBox4.setSelected(false);
            checkBox5.setSelected(false);
            textArea.setText(lambda + "=\nt=\nm(t)=\n" + sigma + "=\nk=\na=");
            textArea2.setText(getVarHelp("P(t)", "q(t)", lambda + "(t)", sigma, "f(t)", "m(t)", "N", "t", "a", "k"));
        });
        checkBox3.addActionListener(actionEvent -> {
            checkBox2.setSelected(false);
            checkBox1.setSelected(false);
            checkBox4.setSelected(false);
            checkBox5.setSelected(false);
            textArea.setText(lambda + "=\nl[]=\nN=\nt=\nm(t)[]=\np(t)[]=\np(t)=");
            textArea2.setText(getVarHelp("P(t)", "q(t)", lambda + "(t)", "f(t)", "m(t)", "N", "t"));
        });
        checkBox4.addActionListener(actionEvent -> {
            checkBox2.setSelected(false);
            checkBox3.setSelected(false);
            checkBox1.setSelected(false);
            checkBox5.setSelected(false);
            textArea.setText("N=\nm=\nm(t)=\nt=\n" + lambda + "(t)=");
            textArea2.setText(getVarHelp("P(t)", "q(t)", lambda + "(t)", "f(t)", "m(t)", "N", "t"));
        });
        checkBox5.addActionListener(actionEvent -> {
            checkBox2.setSelected(false);
            checkBox3.setSelected(false);
            checkBox4.setSelected(false);
            checkBox1.setSelected(false);
            textArea.setText("N=\nm=\nm(t)=\nt=\nl0=\nl1=");
            textArea2.setText(getVarHelp("P(t)", "q(t)", lambda + "(t)", "f(t)", "m(t)", "N", "t"));
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Dnas();
    }
}
