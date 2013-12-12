/**
 * Date:	12.12.2013:8:16:44
 * File:	OperationReplace.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces.impl;

import com.unit7.study.cryptography.tools.Pair;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Stack;

public class OperationReplace extends AbstractOperation {

	/* (non-Javadoc)
	 * @see com.unit7.study.translationmethods.labs.lab4.interfaces.Operation#execute(java.lang.Object)
	 */
	@Override
	public void execute(Pair<String, Stack<String>> e) {
		e.getSecond().pop();
		e.getSecond().push(e.getFirst());
	}

}
