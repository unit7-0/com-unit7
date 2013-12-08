package com.unit7.study.translationmethods.labs.lab3.interfaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;

public class StateImpl implements State {
    /**
     * Построение State - состояние автомата. На входе двумерный массив. Первый
     * элемент строки - состояние, второй - переход, третий - новое состояние в
     * результате перехода.
     * 
     * @param rawStates
     * @param states
     *            мапа состояний, имя -> состояние
     * @return та же мапа с новыми состояниями
     * @throws InformationException
     */
    public static Map<String, State> createState(String[][] rawStates, Map<String, State> states)
            throws InformationException {
        for (int i = 0; i < rawStates.length; ++i) {
            String name = rawStates[i][0];
            String jump = rawStates[i][1];
            String next = rawStates[i][2];

            StateImpl state = (StateImpl) states.get(name);
            if (state == null) {
                state = new StateImpl(name);
                states.put(name, state);
            }

            // возможно переход уже есть => ошибка
            if (state.jumps.get(jump) != null)
                throw new InformationException(String.format(JUMP_EXIST, jump, name));

            // возможно состояние уже есть
            State jumpToState = states.get(next);
            if (jumpToState == null) {
                jumpToState = new StateImpl(next);
                states.put(next, jumpToState);
            }

            state.jumps.put(jump, jumpToState);
        }

        return states;
    }

    public StateImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasNextState(String c) {
        return jumps.get(c) != null;
    }

    @Override
    public State nextState(String c) {
        return jumps.get(c);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StateImpl))
            return false;
        
        StateImpl other = (StateImpl) obj;
        return name.equals(other.name) && jumps.equals(other.jumps);
    }



    private String name;
    private Map<String, State> jumps = new HashMap<String, State>();
    
    public static final String JUMP_EXIST = "Переход %s из состояния %s уже есть";
}
