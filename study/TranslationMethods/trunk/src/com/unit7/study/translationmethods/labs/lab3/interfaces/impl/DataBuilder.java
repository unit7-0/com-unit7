package com.unit7.study.translationmethods.labs.lab3.interfaces.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp;
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;

public class DataBuilder {
    public String parseChain(HttpServletRequest request) throws InformationException {
        String str = request.getParameter(AutomateApp.PARAM_CHAIN);
        return parseString(str, "цепочка");
    }
    
    public String parseTerminals(HttpServletRequest request) throws InformationException {
        String str = request.getParameter(AutomateApp.PARAM_TERMINALS);
        return parseString(str, "строка терминалов");
    }
    
    public Map<String, State> parseStates(HttpServletRequest request) throws InformationException {
        Enumeration<String> names = request.getParameterNames();
        
        Map<String, State> states = new HashMap<String, State>();
        List<String> stateNames = new ArrayList<String>();
        
        // набираем все состояния
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.startsWith(AutomateApp.PARAM_STATE_BEG))
                stateNames.add(name);
        }
        
        // хотя бы одно состояние передано
        if (stateNames.isEmpty()) 
            throw new InformationException(String.format(AutomateApp.PARAM_NULL, "состояния"));
        
        String[][] rawStates = new String[stateNames.size()][3];
        StringBuilder message = new StringBuilder();
        
        // для каждого состояния должен быть переход в другое состояние
        for (int i = 0; i < rawStates.length; ++i) {
            String nameParam = stateNames.get(i);
            String nameIndex = nameParam.substring(nameParam.indexOf("$") + 1);
            int number = Integer.parseInt(nameIndex);
            
            String stateName = request.getParameter(nameParam);
            String jump = request.getParameter(AutomateApp.PARAM_JUMP_BEG + number);
            String toState = request.getParameter(AutomateApp.PARAM_TO_STATE_BEG + number);
            
            try {
                stateName = parseString(stateName, "состояние");
                jump = parseString(jump, "переход из состояния");
                toState = parseString(toState, "состояние");
            } catch (InformationException e) {
                appendStr(message, e.getLocalizedMessage());
                continue;
            }
            
            rawStates[i][0] = stateName;
            rawStates[i][1] = jump;
            rawStates[i][2] = toState;
        }
        
        if (message.length() != 0) 
            throw new InformationException(message.toString());
        
        return StateImpl.createState(rawStates, states);
    }
    
    public void appendStr(StringBuilder builder, String message) {
        if (builder.length() != 0)
            builder.append("\r\n");
        
        builder.append(message);
    }
    
    public State parseStartState(HttpServletRequest request, Map<String, State> states) throws InformationException {
        return parseState(AutomateApp.PARAM_START_STATE, "начальное состояние", request, states);
    }
    
    private State parseState(String name, String simpleName, HttpServletRequest request, Map<String, State> states) throws InformationException {
        String str = request.getParameter(name);
        str = parseString(str, simpleName);
        State state = states.get(str);
        
        if (state == null)
            throw new InformationException(String.format(AutomateApp.PARAM_NOT_DESCRIBED, simpleName));
        
        return state;
    }
    
    public State parseFinalState(HttpServletRequest request, Map<String, State> states) throws InformationException {
        return parseState(AutomateApp.PARAM_FINAL_STATE, "конечное состояние", request, states);
    }
    
    private String parseString(String str, String name) throws InformationException {
        if (str == null)
            throw new InformationException(String.format(AutomateApp.PARAM_NULL, name));
        
        str = str.trim();
        str = str.replaceAll("\\s", "");
        
        return str;
    }
}
