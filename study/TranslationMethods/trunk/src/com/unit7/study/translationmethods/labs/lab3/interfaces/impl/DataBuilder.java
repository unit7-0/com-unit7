package com.unit7.study.translationmethods.labs.lab3.interfaces.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp;
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;

public class DataBuilder {
    public String parseChain(Map<String, String> request) throws InformationException {
        String str = request.get(AutomateApp.PARAM_CHAIN);
        
        if (str == null)
            throw new InformationException(String.format(AutomateApp.PARAM_NULL, "Цепочка"));
        
        str = str.trim();
        str = str.replaceAll("\\s", "");
        
        return str.equals("") ? "&" : str;
    }
    
    public String parseTerminals(Map<String, String> request) throws InformationException {
        String str = request.get(AutomateApp.PARAM_TERMINALS);
        return parseString(str, "строка терминалов");
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
        
        String[][] rawStates = new String[stateNames.size()][3];
        StringBuilder message = new StringBuilder();
        
        // для каждого состояния должен быть переход в другое состояние
        for (int i = 0; i < rawStates.length; ++i) {
            String nameParam = stateNames.get(i);
            String nameIndex = nameParam.substring(nameParam.indexOf("$") + 1);
            int number = Integer.parseInt(nameIndex);
            
            String stateName = request.get(nameParam);
            String jump = request.get(AutomateApp.PARAM_JUMP_BEG + number);
            String toState = request.get(AutomateApp.PARAM_TO_STATE_BEG + number);
            
            try {
                stateName = parseString(stateName, "состояние");
                jump = parseString(jump, "переход из состояния " + stateName);
                toState = parseString(toState, "состояние " + stateName);
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
    
    public State parseStartState(Map<String, String> request, Map<String, State> states) throws InformationException {
        return parseState(AutomateApp.PARAM_START_STATE, "начальное состояние", request, states);
    }
    
    private State parseState(String name, String simpleName, Map<String, String> request, Map<String, State> states) throws InformationException {
        String str = request.get(name);
        str = parseString(str, simpleName);
        State state = states.get(str);
        
        if (state == null)
            throw new InformationException(String.format(AutomateApp.PARAM_NOT_DESCRIBED, simpleName));
        
        return state;
    }
    
    public List<State> parseFinalState(Map<String, String> request, Map<String, State> states) throws InformationException {
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
            result.add(state);
        }
        
        return result;
    }
    
    private String parseString(String str, String name) throws InformationException {
        if (str == null)
            throw new InformationException(String.format(AutomateApp.PARAM_NULL, name));
        
        str = str.trim();
        str = str.replaceAll("\\s", "");
        
        if (str.equals(""))
        	throw new InformationException(String.format(AutomateApp.PARAM_NULL, name));
        
        return str;
    }
}
