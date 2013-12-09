package com.unit7.study.translationmethods.labs.lab3.interfaces.impl;

import java.util.HashMap;
import java.util.Map;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.Automate;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateChecker;
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;

public class AutomateCheckerImpl implements AutomateChecker {
    @Override
    public boolean checkAutomate(Automate automate) throws InformationException {
        // stub       
        return true;
    }

    @Override
    public boolean checkTerminals(String terminals) throws InformationException {
        if (terminals.equals(""))
            throw new InformationException(String.format(AutomateChecker.TERMINALS_EXCEPTION, " список пуст"));
        
        String[] terms = terminals.split("");
        Map<String, Integer> counter = new HashMap<String, Integer>();
        for (String term : terms) {
            if (term.equals("&"))
                throw new InformationException(AutomateChecker.EMPTY_NOT_NEED);
            
            Integer count = counter.get(term);
            if (count == null)
                count = 0;
            
            counter.put(term, count + 1);
        }
        
        StringBuilder message = new StringBuilder();
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            if (entry.getValue() != 1) {
                if (message.length() != 0)
                    message.append("\r\n");
                
                message.append("символ " + entry.getKey() + " встречается " + entry.getValue() + " раз(а)");
            }
        }
        
        if (message.length() != 0) {
            throw new InformationException(String.format(AutomateChecker.TERMINALS_EXCEPTION, message.toString()));
        }
        
        return true;
    }

    @Override
    public boolean checkChain(Automate automate, String chain) throws InformationException {
        return automate.checkChain(chain);
    }

}
