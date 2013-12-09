package com.unit7.study.translationmethods.labs.lab3.interfaces;

import java.util.List;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;

public interface Automate {
    boolean checkChain(String chain) throws InformationException;

    List<String> getLog();
}
