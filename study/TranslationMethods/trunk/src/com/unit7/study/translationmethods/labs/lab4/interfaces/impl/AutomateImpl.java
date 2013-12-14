/**
 * Date:	12.12.2013:7:55:11
 * File:	AutomateImpl.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces.impl;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import com.unit7.study.cryptography.tools.Pair;
import com.unit7.study.translationmethods.labs.lab1.GrammarRules;
import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.Automate;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Operation;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Stack;
import com.unit7.study.translationmethods.labs.lab4.interfaces.State;

/**
 * Начальные состояния - так как может быть описано несколько переходов
 * начального состояния, но с разным состоянием стека, выбирается одно из
 * подходящих по входному символу и текущему состоянию стека, очевидно он должен
 * быть пуст.
 */
public class AutomateImpl implements Automate {
    public AutomateImpl(State start, String stack) {
        this.start = start;
        globalStack.push(stack);
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
        // globalStack.push("Z");
        try {
            Pair<String, Stack<String>> operand = new Pair<String, Stack<String>>();
            operand.setSecond(globalStack);

            Pair<State, Operation> current = new Pair(start, null);
            try {
                for (int i = 0; i < chain.length(); ++i) {
                    String c = chain.substring(i, i + 1);
                    State state = current.getFirst();

                    if (state.hasNextState(c, globalStack)) {
                        log.add("state: [ " + state.getName() + " ] symbol: [ " + c + " ] stack top: [ "
                                + globalStack.top() + " ] ");
                        current = state.nextState(c, globalStack);
                        operand.setFirst(c);
                        current.getSecond().execute(operand);
                    } else {
                        // try empty tact
                        String trying = GrammarRules.GRAMMAR_EMPTY;
                        log.add("state: [ " + state.getName() + " ] symbol: [ " + trying + " ] stack top: [ "
                                + globalStack.top() + " ] ");
                        if (state.hasNextState(trying, globalStack)) {
                            current = state.nextState(trying, globalStack);
                            operand.setFirst(trying);
                            current.getSecond().execute(operand);
                            i -= 1;
                            continue;
                        }

                        throw new InformationException(String.format(AutomateApp.NO_JUMP_FOR_CURRENT_STATE, c,
                                state.getName()));
                    }
                }

                while (!globalStack.isEmpty()) {
                    // try empty tact
                    String trying = GrammarRules.GRAMMAR_EMPTY;
                    State state = current.getFirst();
                    log.add("state: [ " + state.getName() + " ] symbol: [ " + trying + " ] stack top: [ "
                            + globalStack.top() + " ] ");
                    if (state.hasNextState(trying, globalStack)) {
                        current = state.nextState(trying, globalStack);
                        operand.setFirst(trying);
                        current.getSecond().execute(operand);
                        continue;
                    }

                    throw new InformationException(String.format(AutomateApp.NO_JUMP_FOR_CURRENT_STATE, trying,
                            state.getName()));
                }

            } catch (EmptyStackException e) {
                throw new InformationException(AutomateApp.EMPTY_STACK_ON_OPERATION);
            }

            log.add("\r\nchain finished. state: [ " + current.getFirst().getName() + " ] stack top: [ "
                    + globalStack.top() + " ]");

            return current.getFirst().isFinal() && globalStack.isEmpty();
        } finally {
            globalStack.clear();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.unit7.study.translationmethods.labs.lab3.interfaces.Automate#getLog()
     */
    @Override
    public List<String> getLog() {
        // just a stub
        List<String> log1 = log;
        log = new ArrayList<String>();
        return log1;
    }

    private State start;
    private List<String> log = new ArrayList<String>();
    private Stack<String> globalStack = new StringStack();
}
