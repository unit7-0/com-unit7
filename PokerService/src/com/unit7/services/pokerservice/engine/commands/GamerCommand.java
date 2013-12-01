package com.unit7.services.pokerservice.engine.commands;

import com.unit7.services.pokerservice.engine.Controller;
import com.unit7.services.pokerservice.model.PokerGamer;

public class GamerCommand extends AbstractCommand {
    @Override
    public void execute(Controller controller) {
        controller.execute(this);
    }

    public PokerGamer getGamer() {
        return gamer;
    }

    public void setGamer(PokerGamer gamer) {
        this.gamer = gamer;
    }

    private PokerGamer gamer;
}
