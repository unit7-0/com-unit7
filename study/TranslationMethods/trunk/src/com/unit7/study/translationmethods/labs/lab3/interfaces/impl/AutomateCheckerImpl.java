package com.unit7.study.translationmethods.labs.lab3.interfaces.impl;

import java.util.HashMap;
import java.util.Map;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.Automate;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateChecker;

public class AutomateCheckerImpl implements AutomateChecker {
    @Override
    public boolean checkAutomate(Automate automate) throws InformationException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkTerminals(String terminals) throws InformationException {
        if (terminals.equals(""))
            throw new InformationException(String.format(AutomateChecker.TERMINALS_EXCEPTION, " список пуст"));
        
        String[] terms = terminals.split("");
        Map<String, Integer> counter = new HashMap<String, Integer>();
        for (String term : terms) {
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
                
                message.append("символ " + entry.getKey() + " встречается " + entry.getValue());
            }
        }
        
        if (message.length() != 0) {
            throw new InformationException(String.format(AutomateChecker.TERMINALS_EXCEPTION, message.toString()));
        }
        
        return true;
    }

    @Override
    public boolean checkChain(Automate automate, String chain) throws InformationException {
        // TODO Auto-generated method stub
        return false;
    }

}
