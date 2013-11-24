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

import com.unit7.study.translationmethods.labs.exceptions.InformationMessageException;

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
        StringBuilder expr = new StringBuilder();
        boolean isOk = false;
        Validator validator = new GrammarValidator();
        Analizer analizer = new RecursionAnalizer();
        Parser parser = new InputDataParser();
        ArrayList<String[]> exprs = new ArrayList();
        String[] allowedStrings = (parser.parse(terminal.getText().trim()
                .replaceAll(" +", " ")) + parser.parse(notTerminal.getText()
                .trim().replaceAll(" +", ""))).split("");
        for (Component comp : rulePanel.getComponents()) {
            if (comp instanceof JLabel) {
                name = String.valueOf(((JLabel) comp).getText().charAt(0));
            } else if (comp instanceof JTextField) {
                expr = new StringBuilder(((JTextField) comp).getText().trim()
                        .replaceAll(" +", " "));
                isOk = true;
            }

            if (isOk) {
                if (expr == null || expr.length() == 0
                        || !validator.validate(expr.toString())) {
                    JOptionPane.showMessageDialog(rulePanel,
                            "Правила заданы неверно!");
                    return;
                }

                String[] tokens = parser.parse(expr.toString()).split("");
                for (String token : tokens) {
                    boolean ex = false;
                    // fuck the perfomanence
                    for (String allowed : allowedStrings) {
                        if (token.equals(allowed)) {
                            ex = true;
                            break;
                        }
                    }

                    if (!ex && !token.equals(GrammarRules.GRAMMAR_EMPTY)
                            && !token.equals("|")) {
                        JOptionPane.showMessageDialog(rulePanel,
                                "Найден незаданный терминал");
                        return;
                    }
                }

                // check expression to recursive dependency
                // and remove it
                String[] parts = expr.toString().split(
                        GrammarRules.GRAMMAR_DELIMETER);
                expr = new StringBuilder();
                for (String part : parts) {
                    try {
                        // кажется это больше не нужно, поэтому true
                        if (false || analizer.analize(new String[] { name, part })) {
                            expr.append(part).append("|");
                        }
                    } catch (InformationMessageException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

                exprs.add(new String[] {
                        name,
                        expr.delete(expr.length() - 1, expr.length() - 1)
                                .toString() });
                isOk = false;
            }
        }

        final Map<String, String> expressions = ((MainInterface) cont)
                .getExpressions();
        for (String[] pair : exprs) {
            expressions.put(pair[0], pair[1]);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                int len = -1;
                try {
                    len = Integer.parseInt(lenField.getText().trim());
                    if (len <= 0)
                        throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Поле длины цепочки задано неверно");
                    return;
                }
                
                new ChainsShower(expressions, target.getText().trim(), len).setVisible(true);
            }
        }).start();
    }

    private JTextField terminal;
    private JTextField notTerminal;
    private JTextField target;
    private JTextField lenField;
    private JPanel rulePanel;
}