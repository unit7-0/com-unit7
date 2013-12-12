/**
 * Date:	12.12.2013:7:34:17
 * File:	Stack.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces;

import java.util.EmptyStackException;

public interface Stack<T> {
	void push(T e);

	T pop() throws EmptyStackException;

	T top() throws EmptyStackException;

	boolean compareTop(T e) throws EmptyStackException;

	int size();
}
