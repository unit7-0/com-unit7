package com.unit7.study.translationmethods.labs.lab1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BuildListener implements ActionListener {
    public BuildListener(JComponent[] args) {
        this.terminal = (JTextField) args[0];
        this.notTerminal = (JTextField) args[1];
        this.target = (JTextField) args[2];
        this.lenField = (JTextField) args[3];
        this.rulePanel = (JPanel) args[4];
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    private JTextField terminal;
    private JTextField notTerminal;
    private JTextField target;
    private JTextField lenField;
    private JPanel rulePanel;
}