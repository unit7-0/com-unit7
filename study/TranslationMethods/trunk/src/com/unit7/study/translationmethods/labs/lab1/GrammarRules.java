package com.unit7.study.translationmethods.labs.lab1;

public class GrammarRules {
    public static final String TERMINALS = "0123456789abcdefghigjklmnopqrstufvwxyz()";
    public static final String NOT_TERMINALS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String DELIMETERS = " ,";
    public static final String PURE_GRAMMAR_DELIMITER = "|";
    public static final String GRAMMAR_DELIMETER = "\\" + PURE_GRAMMAR_DELIMITER;
    public static final String GRAMMAR_EMPTY = "&";
    
    public enum State {
        TERMINAL, NOT_TERMINAL;
    }
    
    public static boolean isTerminal(char target) {
        return TERMINALS.indexOf(target) != -1;
    }
    
    public static boolean isNotTerminal(char target) {
        return NOT_TERMINALS.indexOf(target) != -1;
    }
}
