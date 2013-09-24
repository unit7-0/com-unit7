package com.unit7.study.translationmethods.labs.lab1;

public class GrammarValidator implements Validator {
    @Override
    public boolean validate(Object param) {
        if (!(param instanceof String[]) || ((String[]) param).length != 3)
            throw new IllegalArgumentException("Parameter must be String[] object");
        
        String[] args = (String[]) param;
        String expr = args[0];
        String term = args[1];
        String notTerm = args[2];
        
        String[] tokens = expr.split(GrammarRules.GRAMMAR_DELIMETER);
        for (String token : tokens) {
            int emptyCount = 0;
            for (int i = 0; i < token.length(); ++i) {
                char tok = token.charAt(i);
                boolean ex = true;
                if (GrammarRules.TERMINALS.indexOf(tok) == -1 && GrammarRules.NOT_TERMINALS.indexOf(tok) == -1)
                    ex = false;
                if (tok == GrammarRules.GRAMMAR_EMPTY.charAt(0)) {
                    ex = true;
                    emptyCount += 1;
                }
                
                if (!ex)
                    return false;
            }
            
            if (emptyCount > 1)
                return false;
        }
        
        return true;
    }
}
