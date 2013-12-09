package com.unit7.study.translationmethods.labs.lab3.interfaces.impl;

import java.util.ArrayList;
import java.util.List;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.Automate;
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;

public class AutomateImpl implements Automate {
    public AutomateImpl(State state, List<State> finalState) {
        this.currentState = state;
        this.finalState = finalState;
    }
    
    @Override
    public boolean checkChain(String chain) throws InformationException {
        for (int i = 0; i < chain.length(); ++i) {
            String jump = chain.substring(i, i + 1);
            log.add(currentState.getName() + ": " + chain.substring(i));
            if (!currentState.hasNextState(jump)) {
                throw new InformationException(String.format(NO_JUMP_FOR_CURRENT_STATE, jump, currentState.getName()));
            }
            
            currentState = currentState.nextState(jump);
        }
        
        log.add(currentState.getName() + ": " + "");
        for (State st : finalState) {
            if (currentState.equals(st))
                return true;
        }
        
        return false;
    }

    @Override
    public List<String> getLog() {
        return log;
    }
    
    private List<State> finalState;
    private State currentState;
    
    private List<String> log = new ArrayList<String>();
    
    public static final String NO_JUMP_FOR_CURRENT_STATE = "Отсутствует состояние для перехода %s из состояния %s";
}
