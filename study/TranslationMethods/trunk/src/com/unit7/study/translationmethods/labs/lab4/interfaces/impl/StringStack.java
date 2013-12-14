/**
 * Date:	12.12.2013:7:41:33
 * File:	StackImpl.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces.impl;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import com.unit7.study.translationmethods.labs.lab1.GrammarRules;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Stack;

/**
 * Реализация стека для автомата с магазинной памятью. 
 * 
 */
public class StringStack implements Stack<String> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.translationmethods.labs.lab4.interfaces.Stack#push(java
	 * .lang.Object)
	 */
	@Override
	public void push(String e) {
		stack.add(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unit7.study.translationmethods.labs.lab4.interfaces.Stack#pop()
	 */
	@Override
	public String pop() throws EmptyStackException {
		if (stack.size() < 1)
			throw new EmptyStackException();
		
		return stack.remove(stack.size() - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unit7.study.translationmethods.labs.lab4.interfaces.Stack#top()
	 */
	@Override
	public String top() throws EmptyStackException {
		if (stack.size() < 1)
			return EMPTY_STACK;
		
		return stack.get(stack.size() - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.translationmethods.labs.lab4.interfaces.Stack#compareTop
	 * (java.lang.Object)
	 */
	@Override
	public boolean compareTop(String e) throws EmptyStackException {
		if (stack.size() < 1)
			throw new EmptyStackException();
		
		return stack.get(stack.size() - 1).equals(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.unit7.study.translationmethods.labs.lab4.interfaces.Stack#size()
	 */
	@Override
	public int size() {
		return stack.size();
	}

	@Override
	public boolean isEmpty() {
		return stack.size() == 0;
	}

	private List<String> stack = new ArrayList<String>();
	
	public static final String EMPTY_STACK = " ";

    /* (non-Javadoc)
     * @see com.unit7.study.translationmethods.labs.lab4.interfaces.Stack#clear()
     */
    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String str : stack) {
            builder.append(str);
        }
        
        return builder.reverse().toString();
    }
}
