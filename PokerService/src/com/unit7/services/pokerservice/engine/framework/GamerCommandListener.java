package com.unit7.services.pokerservice.engine.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.unit7.services.pokerservice.engine.commands.GamerCommand;
import com.unit7.services.pokerservice.model.PokerGamer;

public class GamerCommandListener implements EventListener {
    @Override
    public void update(Object data) {
        // TODO full check
        if (!(data instanceof Map<?, ?>)) {
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
