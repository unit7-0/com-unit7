package com.unit7.study.translationmethods.labs.lab1;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
        Container cont = rulePanel.getParent();
        while (cont.getParent() != null) {
            cont = cont.getParent();
        }
        
        String name = "";
        String expr = "";
        boolean isOk = false;
        GrammarValidator validator = new GrammarValidator();
        ArrayList<String[]> exprs = new ArrayList();
        for (Component comp : rulePanel.getComponents()) {
            if (comp instanceof JLabel) {
                name = ((JLabel) comp).getText();
            } else if (comp instanceof JTextField) {
                expr = ((JTextField) comp).getText().trim().replaceAll(" +", " ");
                isOk = true;
            }
            
            if (isOk) {
                if (expr == null || expr.length() == 0 || validator.validate(expr)) {
                    JOptionPane.showMessageDialog(rulePanel, "Правила заданы неверно!");
                    return;
                }
                
                exprs.add(new String[] { name, expr });
                isOk = false;
            }
        }
        
        final Map<String, String> expressions = ((MainInterface) cont).getExpressions();
        for (String[] pair : exprs) {
            expressions.put(pair[0], pair[1]);
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CertainsShower(expressions, target.getText().trim(), Integer.parseInt(lenField.getText().trim())).setVisible(true);
            }
        });
    }

    private JTextField terminal;
    private JTextField notTerminal;
    private JTextField target;
    private JTextField lenField;
    private JPanel rulePanel;
}