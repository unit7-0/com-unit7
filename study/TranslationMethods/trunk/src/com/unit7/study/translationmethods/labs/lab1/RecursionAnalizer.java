package com.unit7.study.translationmethods.labs.lab1;

public class RecursionAnalizer implements Analizer {
    @Override
    public boolean analize(Object param) {
        if (!(param instanceof String[]) || ((String[]) param).length != 2) {
            throw new IllegalArgumentException(
                    "Parameter must be String object and length == 2");
        }

        String target = ((String[]) param)[0].replaceAll("\\s", "");
        String expr = ((String[]) param)[1].replaceAll("\\s", "");

        if (expr.equals(target))
            return false;

        return true;
    }

}
