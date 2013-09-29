package com.unit7.study.translationmethods.labs.lab1;

public class InputDataParser implements Parser {
    @Override
    public String parse(String parse) {
        char delimeter = getDelimeter(parse, GrammarRules.DELIMETERS);

        // if hasn't delimeter
        if (delimeter == (char) -1) {
            return parse;
        } else {
            return parse.replaceAll(String.valueOf(delimeter), "");
        }
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
}
