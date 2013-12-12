/**
 * Date:	12.12.2013:7:21:04
 * File:	StateImpl.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces.impl;

import java.util.Map;

import com.unit7.study.cryptography.tools.Pair;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Stack;
import com.unit7.study.translationmethods.labs.lab4.interfaces.State;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Operation;

public class StateImpl implements State {
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
	private Map<Pair<String, String>, Pair<State, Operation>> jumps;
}
