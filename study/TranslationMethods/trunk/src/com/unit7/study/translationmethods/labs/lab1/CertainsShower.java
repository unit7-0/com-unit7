package com.unit7.study.translationmethods.labs.lab1;

import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;

public class CertainsShower extends JFrame {
    public CertainsShower(Map<String, String> exprs, String target, int lens) {
        super("Цепочки");
        this.expressions = exprs;
    }

    public String buildCertains(String target, int len) {
        String exprs = expressions.get(target);
        String[] vars = exprs.split(GrammarRules.GRAMMAR_DELIMETER);
        String expr = vars[rand.nextInt(vars.length)];

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < expr.length(); ++i) {
            if (GrammarRules.isTerminal(expr.charAt(i))) {
                result.append(expr.charAt(i));
            } else if (GrammarRules.isNotTerminal(expr.charAt(i))) {
                result.append(buildCertains(expr.substring(i, i + 1), len));
            }
        }

        return result.toString();
    }

    private Map<String, String> expressions;
}
