package com.unit7.study.translationmethods.labs.lab3.interfaces.impl;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.Automate;
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;

public class AutomateImpl implements Automate {
    public AutomateImpl(State state) {
        this.currentState = state;
    }
    
    @Override
    public boolean checkChain(String chain) throws InformationException {
        for (int i = 0; i < chain.length(); ++i) {
            String jump = chain.substring(i, i + 1);
            if (!currentState.hasNextState(jump)) {
                throw new InformationException(String.format(NO_JUMP_FOR_CURRENT_STATE, jump, currentState.getName()));
            }
            
            currentState = currentState.nextState(jump);
        }
        
        return true;
    }

    private State currentState;
    
    public static final String NO_JUMP_FOR_CURRENT_STATE = "Отсутствует состояние для перехода %s из состояния %s";
}
