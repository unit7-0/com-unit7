package com.unit7.services.pokerservice.engine.commands;

import java.util.List;

import com.unit7.services.pokerservice.engine.framework.Controller;
import com.unit7.services.pokerservice.model.PokerGamer;

/**
 * Выдает игрокам выигрыш, а также определяет кто остался в игре
 * 
 * @author unit7
 * 
 */
public class EndRoundCommand extends AbstractCommand {
    public EndRoundCommand() {
        setCommandType(CommandType.END_ROUND);
    }

    @Override
    public void execute(Controller controller) {
        controller.execute(this);
    }

    public List<PokerGamer> getGamers() {
        return gamers;
    }

    public void setGamers(List<PokerGamer> gamers) {
        this.gamers = gamers;
    }

    private List<PokerGamer> gamers;
}
