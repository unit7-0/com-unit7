/**
 * Date:	12.12.2013:7:37:15
 * File:	State.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces;

import java.util.List;

import com.unit7.study.cryptography.tools.Pair;

public interface State {
	boolean hasNextState(String c, Stack<String> stack);

	Pair<State, Operation> nextState(String c, Stack<String> stack);
	
	String getName();
	
	boolean isFinal();
	
	void setFinal(boolean state);
	
	List<State> getStatesByStackState(String stack);
}
