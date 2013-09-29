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

import com.unit7.study.translationmethods.labs.exceptions.InformationMessageException;

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
        try {
            new RightDataAnalizer()
                    .analize(new String[] { terminal.getText(),
                            notTerminal.getText(), target.getText(),
                            lenField.getText() });
        } catch (InformationMessageException ex) {
            JOptionPane.showMessageDialog(rulePanel, ex.getLocalizedMessage());
            return;
        }

        String[] notTerminals = new InputDataParser().parse(
                notTerminal.getText().trim().replaceAll(" +", " ")).split("");

        disableFields();
        rulePanel.removeAll();
        rulePanel.setLayout(new GridLayout(notTerminals.length, 2));
        for (int i = 0; i < notTerminals.length; ++i) {
            String val = notTerminals[i];
            if (val.isEmpty())
                continue;
            
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

    private void disableFields() {
        terminal.setEnabled(false);
        notTerminal.setEnabled(false);
        target.setEnabled(false);
        lenField.setEnabled(false);
        clearButton.setEnabled(false);
    }

    private JTextField terminal;
    private JTextField notTerminal;
    private JTextField target;
    private JTextField lenField;
    private JPanel rulePanel;
    private JButton clearButton;
}
