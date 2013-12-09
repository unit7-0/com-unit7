package com.unit7.study.translationmethods.labs.lab3.interfaces;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;

public interface AutomateChecker {
    boolean checkAutomate(Automate automate) throws InformationException;

    boolean checkTerminals(String terminals) throws InformationException;

    boolean checkChain(Automate automate, String chain) throws InformationException;
    
    public static final String TERMINALS_EXCEPTION = "Терминалы не удовлетворяют условиям: %s";
    public static final String EMPTY_NOT_NEED = "Пустой символ не требуется вводить в список терминалов";
}
