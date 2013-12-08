package com.unit7.study.translationmethods.labs.lab3.interfaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unit7.study.translationmethods.labs.lab3.interfaces.State;

public class StateImpl implements State {
    /**
     * Построение State - состояние автомата. На входе двумерный массив. Первый
     * элемент строки - состояние, второй - переход, третий - новое состояние в
     * результате перехода.
     * 
     * @param rawStates
     * @param states мапа состояний, имя -> состояние
     * @return та же мапа с новыми состояниями
     */
    public static Map<String, State> createState(String[][] rawStates, Map<String, State> states) {
        for (int i = 0; i < rawStates.length; ++i) {
            String name = rawStates[i][0];
            String jump = rawStates[i][1];
            String next = rawStates[i][2];

            StateImpl state = (StateImpl) states.get(name);
            if (state == null) {
                state = new StateImpl(name);
                states.put(name, state);
            }

            State jumpToState = new StateImpl(next);
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

    private String name;
    private Map<String, State> jumps = new HashMap<String, State>();
}
