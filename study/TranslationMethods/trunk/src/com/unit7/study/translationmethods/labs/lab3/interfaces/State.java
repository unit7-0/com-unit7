package com.unit7.study.translationmethods.labs.lab3.interfaces;

import java.util.List;

public interface State {
    boolean hasNextState(String c);

    State nextState(String c);

    String getName();
}
