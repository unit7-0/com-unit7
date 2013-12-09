package com.unit7.study.translationmethods.labs.lab3.interfaces;

import java.util.List;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;

public interface AutomateApp {
    boolean execute() throws InformationException;
    List<String> getLog();
    
    public static final String PARAM_NULL = "Параметр %s не задан";
    public static final String PARAM_NOT_DESCRIBED = "Параметр %s не описан";
    public static final String STATE_IS_NOT_FINAL = "Разбор остановлен не в конечном состоянии";
    
    public static final String PARAM_CHAIN = "chain_field";
    public static final String PARAM_TERMINALS = "terminals_field";
    public static final String PARAM_STATE_BEG = "state$";
    public static final String PARAM_TO_STATE_BEG = "to_state$";
    public static final String PARAM_JUMP_BEG = "jump$";
    public static final String PARAM_START_STATE = "start_state_field";
    public static final String PARAM_FINAL_STATE = "final_state_field";
}
