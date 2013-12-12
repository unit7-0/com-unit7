/**
 * Date:	12.12.2013:7:21:04
 * File:	StateImpl.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.unit7.study.cryptography.tools.Pair;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Stack;
import com.unit7.study.translationmethods.labs.lab4.interfaces.State;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Operation;

public class StateImpl implements State {
    public StateImpl(String name) {
        this.name = name;
    }
    
    public static Map<String, State> createStates(String[][] rawStates, List<Operation> operations, Map<String, State> states) {
        for (int i = 0; i < rawStates.length; ++i) {
            String name = rawStates[i][0];
            String jump = rawStates[i][1];
            String stack = rawStates[i][2];
            String toName = rawStates[i][3];
            Operation op = operations.get(i);
            
            StateImpl state = (StateImpl) states.get(name);
            if (state == null) {
                state = new StateImpl(name);
                states.put(name, state);
            }
            
            State toState = states.get(toName);
            if (toState == null) {
                toState = new StateImpl(toName);
                states.put(toName, toState);
            }
            
            state.jumps.put(new Pair(jump, stack),  new Pair(toState, op));
        }
        
        return states;
    }
    
	@Override
	public boolean hasNextState(String c, Stack<String> stack) {
		Pair<String, String> trying = new Pair<String, String>(c, stack.top());
		return jumps.containsKey(trying);
	}

	@Override
	public Pair<State, Operation> nextState(String c, Stack<String> stack) {
		Pair<String, String> trying = new Pair<String, String>(c, stack.top());
		return jumps.get(trying);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isFinal() {
		return isFinal;
	}

	@Override
	public void setFinal(boolean state) {
		this.isFinal = state;
	}

    private String name;
	private boolean isFinal;
	private Map<Pair<String, String>, Pair<State, Operation>> jumps = new HashMap<Pair<String,String>, Pair<State,Operation>>();
}
