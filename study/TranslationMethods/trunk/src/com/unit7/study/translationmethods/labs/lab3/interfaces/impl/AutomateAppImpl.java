package com.unit7.study.translationmethods.labs.lab3.interfaces.impl;

import java.util.List;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.Automate;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateChecker;
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;

public class AutomateAppImpl implements AutomateApp {
    public AutomateAppImpl(String terms, String chain, State start, List<State> finalState) {
        this.terms = terms;
        this.chain = chain;
        this.start = start;

        automate = new AutomateImpl(start, finalState);
    }

    @Override
    public boolean execute() throws InformationException {
        return checker.checkTerminals(terms) && checker.checkAutomate(automate) && checker.checkChain(automate, chain);
    }    
    
    @Override
    public List<String> getLog() {
        return automate.getLog();
    }

    private String terms;
    private String chain;
    private State start;

    private Automate automate;
    private AutomateChecker checker = new AutomateCheckerImpl();
}
