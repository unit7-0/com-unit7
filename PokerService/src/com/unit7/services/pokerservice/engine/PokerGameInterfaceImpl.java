package com.unit7.services.pokerservice.engine;

import java.util.List;

import com.unit7.services.pokerservice.engine.commands.BigBlindCommand;
import com.unit7.services.pokerservice.engine.commands.GamerCommand;
import com.unit7.services.pokerservice.engine.commands.RequestNameCommand;
import com.unit7.services.pokerservice.engine.commands.SmallBlindCommand;
import com.unit7.services.pokerservice.model.PokerGamer;

public class PokerGameInterfaceImpl implements PokerGameInterface {
    public PokerGameInterfaceImpl(List<PokerGamer> gamers) {
        this.gamers = gamers;
    }

    @Override
    public void distributeCards() {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestSmallBlind(PokerGamer gamer) {
        GamerCommand command = new SmallBlindCommand();
        command.setGamer(gamer);
        command.equals(Controller.getInstance());
    }

    @Override
    public void requestBigBlind(PokerGamer gamer) {
        GamerCommand command = new BigBlindCommand();
        command.setGamer(gamer);
        command.equals(Controller.getInstance());
    }

    @Override
    public void game() {
        // step one - request usernames
        requestNames();
        
        // step two - select button
        PokerGamer button = selectButton();
        
        // step three - get small blind
        PokerGamer gamer = gamers.get((lastButton + 1) % gamers.size());
        requestSmallBlind(gamer);
        
        // step four - get big blind
        gamer = gamers.get((lastButton + 2) % gamers.size());
        requestBigBlind(gamer);
    }

    @Override
    public PokerGamer selectButton() {
        if (lastButton == -1) {
            lastButton = com.unit7.services.pokerservice.tools.Utils.getRandInt(gamers.size());
        } else {
            lastButton = ++lastButton % gamers.size();
        }
        
        return gamers.get(lastButton);
    }
    
    protected void requestNames() {
        for (PokerGamer gamer : gamers) {
            RequestNameCommand command = new RequestNameCommand();
            command.setGamer(gamer);
            command.execute(Controller.getInstance());
        }
    }
    
    private List<PokerGamer> gamers;
    private int lastButton = -1;
}
