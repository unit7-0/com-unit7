/**
 * Date:	15 дек. 2013 г.
 * File:	MainForm.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.rgz.var7;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.unit7.study.translationmethods.rgz.var7.processors.ChainCreator;
import com.unit7.study.translationmethods.rgz.var7.processors.RegExpCreator;

/**
 * @author unit7
 *
 */
public class MainForm extends JFrame {
    public static void main(String[] args) {
        new MainForm().createGUI().setVisible(true);
    }
    
    public JFrame createGUI() {
        JPanel main = new JPanel(new GridLayout(5, 1, 10, 10));

        JPanel alph = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel begChain = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel mulChar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel lens = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JLabel alphLabel = new JLabel("Алфавит: ");
        JLabel begChainLabel = new JLabel("Начальная подцепочка: ");
        JLabel charLabel = new JLabel("Символ: ");
        JLabel mulCharLabel = new JLabel("Кратность: ");
        JLabel lensLabel = new JLabel("Диапазон длин: ");

        final int defaultFieldLen = 10;

        JTextField alphField = new JTextField(defaultFieldLen);
        JTextField begChainField = new JTextField(defaultFieldLen);
        JTextField charField = new JTextField(defaultFieldLen);
        JTextField mulCharField = new JTextField(defaultFieldLen);
        JTextField lens1Field = new JTextField(defaultFieldLen);
        JTextField lens2Field = new JTextField(defaultFieldLen);

        alph.add(alphLabel);
        alph.add(alphField);

        begChain.add(begChainLabel);
        begChain.add(begChainField);

        mulChar.add(charLabel);
        mulChar.add(charField);
        mulChar.add(mulCharLabel);
        mulChar.add(mulCharField);

        lens.add(lensLabel);
        lens.add(lens1Field);
        lens.add(lens2Field);

        JButton createGrammar = new JButton("Показать регулярное выражение");
        JButton showChains = new JButton("Показать цепочки");

        RegExpCreator regExpCreator = new RegExpCreator(alphField, begChainField, charField, mulCharField, lens1Field, lens2Field);
        ChainCreator chainCreator = new ChainCreator(regExpCreator);
        
        createGrammar.addActionListener(regExpCreator);
        showChains.addActionListener(chainCreator);
        
        controls.add(createGrammar);
        controls.add(showChains);

        main.add(alph);
        main.add(begChain);
        main.add(mulChar);
        main.add(lens);
        main.add(controls);
        
        setTitle("RGZ7");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(main);
        pack();
        return this;
    }
}
