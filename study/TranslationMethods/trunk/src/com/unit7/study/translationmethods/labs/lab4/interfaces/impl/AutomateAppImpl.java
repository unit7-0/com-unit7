/**
 * Date:	12 дек. 2013 г.
 * File:	AutomateAppImpl.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces.impl;

import java.util.List;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateChecker;
import com.unit7.study.translationmethods.labs.lab3.interfaces.impl.AutomateCheckerImpl;
import com.unit7.study.translationmethods.labs.lab4.interfaces.State;

/**
 * @author unit7
 *
 */
public class AutomateAppImpl implements AutomateApp {
    public AutomateAppImpl(String chain, State startState, String stack) {
        this.chain = chain;
        automate = new AutomateImpl(startState, stack);
    }
    
    /* (non-Javadoc)
     * @see com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp#execute()
     */
    @Override
    public boolean execute() throws InformationException {
        return checker.checkAutomate(automate) && checker.checkChain(automate, chain);
    }

    /* (non-Javadoc)
     * @see com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp#getLog()
     */
    @Override
    public List<String> getLog() {
        return automate.getLog();
    }

    private AutomateChecker checker = new AutomateCheckerImpl();
    private AutomateImpl automate;
    private String chain;
}
