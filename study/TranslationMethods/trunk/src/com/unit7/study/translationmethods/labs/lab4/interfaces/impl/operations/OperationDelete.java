/**
 * Date:	12.12.2013:8:13:58
 * File:	OperationDelete.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces.impl.operations;

import com.unit7.study.cryptography.tools.Pair;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Stack;
import com.unit7.study.translationmethods.labs.lab4.interfaces.impl.AbstractOperation;

public class OperationDelete extends AbstractOperation {

	/* (non-Javadoc)
	 * @see com.unit7.study.translationmethods.labs.lab4.interfaces.Operation#execute(java.lang.Object)
	 */
	@Override
	public void execute(Pair<String, Stack<String>> e) {
		e.getSecond().pop();
	}

}
