package com.unit7.study.translationmethods.labs.lab1;

import java.util.ArrayList;
import java.util.Map;

public class LeftRecursionRemover implements RecursionRemover {
    @Override
    public void remove(Map<String, String> expressions) {
        // for each symbol we go through all rules and check if we go into this
        // symbol and return back
        for (Map.Entry<String, String> entry : expressions.entrySet()) {
            String target = entry.getKey();
            String[] rules = entry.getValue().split(GrammarRules.GRAMMAR_DELIMETER);
            // remove common recursion
            for (String rule : rules) {
                replaceRule(rule, expressions, target);
            }

            boolean wasChanged = false;
            // remove direct left recursion
            for (String rule : rules) {
                
            }
            if (!wasChanged) {
                break;
            }
        }
    }

    protected void replaceRule(String rule, Map<String, String> expressions, String target) {
        if (beginFromNotTerminal(rule)) {
            for (Map.Entry<String, String> checkEntry : expressions.entrySet()) {
                if (checkEntry.getKey().equals(target))
                    break;
                // @rule begined from our notTerminal => replace this
                // by all rules from @rule
                StringBuilder newRules = new StringBuilder();
                if (beginFromName(rule, checkEntry.getKey())) {
                    String suffix = rule.substring(checkEntry.getKey().length());
                    String[] checkRules = checkEntry.getValue().split(GrammarRules.GRAMMAR_DELIMETER);
                    for (String checkRule : checkRules) {
                        newRules.append(checkRule).append(suffix).append(GrammarRules.PURE_GRAMMAR_DELIMITER);
                    }

                    newRules.deleteCharAt(newRules.length() - 1);
                }

                String targetRule = expressions.get(target);
                int index = 0;
                while (true) {
                    index = targetRule.indexOf(rule, index);
                    if (index + rule.length() == targetRule.length()
                            || targetRule.charAt(index + rule.length() - 1) == GrammarRules.PURE_GRAMMAR_DELIMITER
                                    .charAt(0)) {
                        StringBuilder newRule = new StringBuilder(targetRule.substring(0, index)).append(
                                newRules.toString()).append(
                                targetRule.substring(index + rule.length(), targetRule.length()));
                        break;
                    } else {
                        index += rule.length();
                    }
                }
            }
        }
    }

    /**
     * adaptive to digitNames
     * 
     * @param str
     * @param name
     * @return
     */
    protected boolean beginFromName(String str, String name) {
        if (str.startsWith(name)) {
            // sample: str = A123, name = A12
            if (str.length() > name.length() && Character.isDigit(str.charAt(name.length())))
                return false;
            return true;
        }

        return false;
    }

    protected boolean beginFromNotTerminal(String rule) {
        return GrammarRules.NOT_TERMINALS.indexOf(rule.charAt(0)) != -1;
    }

    private NameToolz nameToolz = new DigitNameToolz();
}
