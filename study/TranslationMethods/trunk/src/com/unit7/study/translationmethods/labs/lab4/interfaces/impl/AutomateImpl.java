/**
 * Date:	12.12.2013:7:55:11
 * File:	AutomateImpl.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces.impl;

import java.util.ArrayList;
import java.util.List;

import com.unit7.study.cryptography.tools.Pair;
import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.Automate;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Operation;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Stack;
import com.unit7.study.translationmethods.labs.lab4.interfaces.State;

/**
 * Начальные состояния - так как может быть описано несколько переходов
 * начального состояния, но с разным состоянием стека, выбирается одно из
 * подходящих по входному символу и текущему состоянию стека, очевидно он должен быть пуст.
 */
public class AutomateImpl implements Automate {
	public AutomateImpl(List<State> beginStates) {
		this.states = beginStates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.translationmethods.labs.lab3.interfaces.Automate#checkChain
	 * (java.lang.String)
	 */
	@Override
	public boolean checkChain(String chain) throws InformationException {
		// select first state
		int count = 0;
		Pair<State, Operation> current = null;
		String firstChar = chain.substring(0, 1);
		for (State state : states) {
			if (state.hasNextState(firstChar, globalStack)) {
				++count;
				current = state.nextState(firstChar, globalStack);
			}
		}
		
		if (current == null)
			throw new InformationException();
		
		if (count > 1) 
			throw new InformationException();
		
		Pair<String, Stack<String>> operand = new Pair<String, Stack<String>>();
		operand.setFirst(firstChar);
		operand.setSecond(globalStack);
		
		current.getSecond().execute(operand);
		
		// remain chain
		for (int i = 0; i < chain.length(); ++i) {
			// TODO
		}		
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.translationmethods.labs.lab3.interfaces.Automate#getLog()
	 */
	@Override
	public List<String> getLog() {
		return log;
	}

	List<State> states;
	List<String> log = new ArrayList<String>();
	Stack<String> globalStack = new StringStack();
}
