package com.unit7.study.translationmethods.labs.lab3.interfaces.impl;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp;
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;

public class AutomateAppImpl implements AutomateApp {
    public AutomateAppImpl(String terms, String chain, State start) {
        this.terms = terms;
        this.chain = chain;
        this.start = start;
    }
    
    @Override
    public boolean execute() throws InformationException {
        throw new InformationException("not supported now");
    }

    private String terms;
    private String chain;
    private State start;
}
