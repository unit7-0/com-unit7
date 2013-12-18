/**
 * Date:	15 дек. 2013 г.
 * File:	MainForm.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.rgz.var5;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.unit7.study.translationmethods.labs.lab1.ChainsShower;
import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.rgz.var5.processors.Creator;
import com.unit7.study.translationmethods.rgz.var5.processors.GrammarCreator;

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
        JPanel endChain = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel lens = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JLabel alphLabel = new JLabel("Алфавит: ");
        JLabel begChainLabel = new JLabel("Начальная подцепочка: ");
        JLabel endChainLabel = new JLabel("Конечная подцепочка: ");
        JLabel lensLabel = new JLabel("Диапазон длин: ");

        final int defaultFieldLen = 10;

        JTextField alphField = new JTextField(defaultFieldLen);
        JTextField begChainField = new JTextField(defaultFieldLen);
        JTextField endChainField = new JTextField(defaultFieldLen);
        JTextField lens1Field = new JTextField(defaultFieldLen);
        JTextField lens2Field = new JTextField(defaultFieldLen);

        alph.add(alphLabel);
        alph.add(alphField);

        begChain.add(begChainLabel);
        begChain.add(begChainField);

        endChain.add(endChainLabel);
        endChain.add(endChainField);

        lens.add(lensLabel);
        lens.add(lens1Field);
        lens.add(lens2Field);

        JButton createGrammar = new JButton("Показать грамматику");
        JButton showChains = new JButton("Показать цепочки");

        final GrammarCreator grammarCreator = new GrammarCreator(alphField, begChainField, endChainField, lens1Field,
                lens2Field);
        
        createGrammar.addActionListener(grammarCreator);
        showChains.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Grammar grammar = null;
                try {
                    grammar = grammarCreator.create();
                } catch (InformationException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                ChainsShower shower = new ChainsShower(grammar.getRules(), grammar.getTarget(), grammar.getMaxLen());
                shower.setVisible(true);
            }
        });

        controls.add(createGrammar);
        controls.add(showChains);

        main.add(alph);
        main.add(begChain);
        main.add(endChain);
        main.add(lens);
        main.add(controls);

        setContentPane(main);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("RGZ unit7");
        pack();

        return this;
    }
}