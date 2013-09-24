package com.unit7.study.translationmethods.labs.lab1;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PrepareListener implements ActionListener {
    public PrepareListener(JComponent[] args) {
        this.terminal = (JTextField) args[0];
        this.notTerminal = (JTextField) args[1];
        this.target = (JTextField) args[2];
        this.lenField = (JTextField) args[3];
        this.rulePanel = (JPanel) args[4];
        this.clearButton = (JButton) args[5];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String terminalsRaw = terminal.getText().trim().replaceAll(" +", " ");
        String notTerminalsRaw = notTerminal.getText().trim().replaceAll(" +", " ");
        String targetSymbolRaw = target.getText().trim().replaceAll(" +", " ");
        String lenRaw = lenField.getText().trim().replaceAll(" +", " ");

        if (isCleaned(targetSymbolRaw) || isCleaned(lenRaw)
                || isCleaned(notTerminalsRaw) || isCleaned(terminalsRaw)) {
            JOptionPane.showMessageDialog(rulePanel.getParent(),
                    "Все поля обязательны для заполнения!");
            return;
        }

        char delimeter = getDelimeter(terminalsRaw, GrammarRules.DELIMETERS);
        String[] terminals = null;

        if (delimeter == (char) -1) {
            terminals = new String[terminalsRaw.length()];
            for (int i = 0; i < terminalsRaw.length(); ++i) {
                terminals[i] = terminalsRaw.substring(i, i + 1);
            }
        } else {
            terminals = terminalsRaw.split(String.valueOf(delimeter));
        }

        for (String terminal : terminals) {
            if (terminal.length() != 1
                    || GrammarRules.TERMINALS.indexOf(terminal) == -1) {
                JOptionPane.showMessageDialog(rulePanel.getParent(),
                        "Неверное выражение в поле терминалов");
                return;
            }
        }
        
        Arrays.sort(terminals);
        if (!checkUnique(terminals)) {
            JOptionPane.showMessageDialog(rulePanel.getParent(),
                    "Неверное выражение в поле терминалов");
            return;
        }

        delimeter = getDelimeter(notTerminalsRaw, GrammarRules.DELIMETERS);
        String[] notTerminals = null;
        if (delimeter == (char) -1) {
            notTerminals = new String[notTerminalsRaw.length()];
            for (int i = 0; i < notTerminalsRaw.length(); ++i) {
                notTerminals[i] = notTerminalsRaw.substring(i, i + 1);
            }
        } else {
            notTerminals = notTerminalsRaw.split(String.valueOf(delimeter));
        }

        for (String terminal : notTerminals) {
            if (terminal.length() != 1
                    || GrammarRules.NOT_TERMINALS.indexOf(terminal) == -1) {
                JOptionPane.showMessageDialog(rulePanel.getParent(),
                        "Неверное выражение в поле не терминалов");
                return;
            }
        }

        Arrays.sort(notTerminals);
        if (!checkUnique(notTerminals)) {
            JOptionPane.showMessageDialog(rulePanel.getParent(),
                    "Неверное выражение в поле не терминалов");
            return;
        }

        if (targetSymbolRaw.length() != 1
                || notTerminalsRaw.indexOf(targetSymbolRaw) == -1) {
            JOptionPane.showMessageDialog(rulePanel.getParent(),
                    "Целевой символ задан неверно");
            return;
        }

        int len = -1;
        try {
            len = Integer.parseInt(lenRaw);
            if (len <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(rulePanel,
                    "Поле длины цепочки задано неверно");
            return;
        }

        disableFields();
        rulePanel.removeAll();
        rulePanel.setLayout(new GridLayout(notTerminals.length, 2));
        for (int i = 0; i < notTerminals.length; ++i) {
            String val = notTerminals[i];
            JLabel label = new JLabel(val + " ->\t");
            JTextField field = new JTextField();
            label.setLabelFor(field);
            rulePanel.add(label);
            rulePanel.add(field);
        }

        Container cont = rulePanel.getParent();
        while (cont.getParent() != null) {
            cont = cont.getParent();
        }

        ((JFrame) cont).pack();
    }

    private boolean isCleaned(String arg) {
        return arg == null || arg.length() == 0;
    }

    private void disableFields() {
        terminal.setEnabled(false);
        notTerminal.setEnabled(false);
        target.setEnabled(false);
        lenField.setEnabled(false);
        clearButton.setEnabled(false);
    }

    private char getDelimeter(String expr, String dels) {
        char delimeter = (char) -1;
        for (int i = 0; i < dels.length(); ++i) {
            if (expr.indexOf(dels.charAt(i)) != -1) {
                delimeter = dels.charAt(i);
                break;
            }
        }

        return delimeter;
    }

    private boolean checkUnique(String[] str) {
        for (int i = 0; i < str.length - 1; ++i) {
            if (str[i].equals(str[i + 1]))
                return false;
        }

        return true;
    }

    private JTextField terminal;
    private JTextField notTerminal;
    private JTextField target;
    private JTextField lenField;
    private JPanel rulePanel;
    private JButton clearButton;
}
