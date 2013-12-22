package com.unit7.services.pokerservice.engine.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.unit7.services.pokerservice.engine.commands.GamerCommand;
import com.unit7.services.pokerservice.model.PokerGamer;

/**
 * TODO refactor this
 * @author unit7
 *
 */
public class GamerCommandListener implements EventListener<Map<PokerGamer, GamerCommand>> {
    @Override
    public void update(Map<PokerGamer, GamerCommand> data) {        
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
