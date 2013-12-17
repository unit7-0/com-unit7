package com.unit7.services.pokerservice.engine.framework;

import java.util.ArrayList;
import java.util.List;

public class GamersChangeListener implements EventListener {

    @Override
    public void update(Object data) {
        // TODO full check
        if (!(data instanceof List)) {
            return;
        }

        for (Subject subj : subjects) {
            subj.update(data);
        }
    }

    @Override
    public EventListener add(Subject subject) {
        subjects.add(subject);
        return this;
    }

    private List<Subject> subjects = new ArrayList<Subject>();
}
