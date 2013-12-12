/**
 * Date:	12 дек. 2013 г.
 * File:	DataBuilder.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4.interfaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.unit7.study.translationmethods.labs.lab1.GrammarRules;
import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp;
import com.unit7.study.translationmethods.labs.lab4.interfaces.State;
import com.unit7.study.translationmethods.labs.lab4.interfaces.Operation;
import com.unit7.study.translationmethods.labs.lab4.interfaces.impl.operations.OperationAdd;
import com.unit7.study.translationmethods.labs.lab4.interfaces.impl.operations.OperationDelete;
import com.unit7.study.translationmethods.labs.lab4.interfaces.impl.operations.OperationReplace;
import com.unit7.study.translationmethods.labs.lab4.interfaces.impl.operations.OperationStub;

/**
 * @author unit7
 *
 */
public class DataBuilder {

    public String parseChain(Map<String, String> request) throws InformationException {
        return dataBuilder.parseChain(request);
    }

    public void appendStr(StringBuilder builder, String message) {
        dataBuilder.appendStr(builder, message);
    }

    public State parseStartState(Map<String, String> request, Map<String, State> states) throws InformationException {
        String str = request.get(AutomateApp.PARAM_START_STATE);
        str = parseString(str, "начальное состояние");
        State state = states.get(str);
        
        if (state == null)
            throw new InformationException(String.format(AutomateApp.PARAM_NOT_DESCRIBED, "начальное состояние"));
        
       return state; 
    }

    public List<State> parseFinalState(Map<String, String> request, Map<String, State> states)
            throws InformationException {
        String str = request.get(AutomateApp.PARAM_FINAL_STATE);
        if (str == null || (str = str.trim()).equals(""))
            throw new InformationException(String.format(AutomateApp.PARAM_NOT_DESCRIBED, "конечное состояние"));
        
        String[] finals = str.split("\\s");
        Set<String> finalsSet = new HashSet<String>();
        for (String fin : finals) {
            if (!fin.equals("")) {
                finalsSet.add(fin);
            }
        }
        
        List<State> result = new ArrayList<State>();
        for (String s : finalsSet) {
            if (!states.containsKey(s)) {
                throw new InformationException(String.format(AutomateApp.PARAM_NOT_DESCRIBED, s));
            }
            
            State state = states.get(s);
            state.setFinal(true);
            result.add(state);
        }
        
        return result;
    }

    public Map<String, State> parseStates(Map<String, String> request) throws InformationException {
        Iterator<String> names = request.keySet().iterator();
        
        Map<String, State> states = new HashMap<String, State>();
        List<String> stateNames = new ArrayList<String>();
        
        // набираем все состояния
        while (names.hasNext()) {
            String name = names.next();
            if (name.startsWith(AutomateApp.PARAM_STATE_BEG))
                stateNames.add(name);
        }
        
        // хотя бы одно состояние передано
        if (stateNames.isEmpty()) 
            throw new InformationException(String.format(AutomateApp.PARAM_NULL, "состояния"));
        
        List<Operation> ops = new ArrayList<Operation>();
        String[][] rawStates = new String[stateNames.size()][4];
        StringBuilder message = new StringBuilder();
        
        // для каждого состояния должен быть переход в другое состояние
        for (int i = 0; i < rawStates.length; ++i) {
            String nameParam = stateNames.get(i);
            String nameIndex = nameParam.substring(nameParam.indexOf("$") + 1);
            int number = Integer.parseInt(nameIndex);
            
            String stateName = request.get(nameParam);
            String jump = request.get(AutomateApp.PARAM_JUMP_BEG + number);
            String stack = request.get(AutomateApp.PARAM_STACK_BEG + number);
            String toState = request.get(AutomateApp.PARAM_TO_STATE_BEG + number);
            String toStack = request.get(AutomateApp.PARAM_STACK_TO_BEG + number);
            
            Operation op = null;
            
            try {
                stateName = parseString(stateName, "состояние");
                jump = parseString(jump, "переход из состояния " + stateName);
                toState = parseString(toState, "состояние " + stateName);
                stack = parseString(stack, "состояние стека до перехода");
                toStack = parseString(toStack, "состояние стека после перехода");
                op = parseOperation(stateName, toState, jump, stack, toStack);
            } catch (InformationException e) {
                appendStr(message, e.getLocalizedMessage());
                continue;
            }
            
            rawStates[i][0] = stateName;
            rawStates[i][1] = jump;
            rawStates[i][2] = stack;
            rawStates[i][3] = toState;
            ops.add(op);
        }
        
        if (message.length() != 0) 
            throw new InformationException(message.toString());
     
        return StateImpl.createStates(rawStates, ops, states);
    }

    public Operation parseOperation(String qs, String qf, String in, String stackIn, String stackOut) throws InformationException {
        if (in.length() != 1 || stackIn.length() != 1 || stackOut.length() > 2 || stackOut.length() < 1)
            throw new InformationException(String.format(AutomateApp.INVALID_OPEARTION_TYPE, qs, in, stackIn, qf, stackOut));
        
        // doNothing
        if (stackIn.equals(stackOut)) {
            return new OperationStub();
        } 
        // add
        else if (stackOut.length() == 2 && stackOut.charAt(0) == in.charAt(0) && stackOut.charAt(1) == stackIn.charAt(0)) {
            return new OperationAdd();
        } 
        // delete
        else if (stackOut.equals(GrammarRules.GRAMMAR_EMPTY)) {
            return new OperationDelete();
        } 
        // replace
        else if (!stackIn.equals(StringStack.EMPTY_STACK) && !stackIn.equals(stackOut)) {
            return new OperationReplace(stackOut);
        }  
        
        throw new InformationException(String.format(AutomateApp.INVALID_OPEARTION_TYPE, qs, in, stackIn, qf, stackOut));
    }
    
    public String parseString(String str, String name) throws InformationException {
        return dataBuilder.parseString(str, name);
    }

    private com.unit7.study.translationmethods.labs.lab3.interfaces.impl.DataBuilder dataBuilder = new com.unit7.study.translationmethods.labs.lab3.interfaces.impl.DataBuilder();
}
