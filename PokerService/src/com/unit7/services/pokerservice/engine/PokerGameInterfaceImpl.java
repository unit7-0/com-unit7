package com.unit7.services.pokerservice.engine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.unit7.services.pokerservice.client.model.Card;
import com.unit7.services.pokerservice.engine.commands.BigBlindCommand;
import com.unit7.services.pokerservice.engine.commands.Command;
import com.unit7.services.pokerservice.engine.commands.GamerCommand;
import com.unit7.services.pokerservice.engine.commands.GetCardCommand;
import com.unit7.services.pokerservice.engine.commands.RequestNameCommand;
import com.unit7.services.pokerservice.engine.commands.SmallBlindCommand;
import com.unit7.services.pokerservice.model.PokerGamer;

public class PokerGameInterfaceImpl implements PokerGameInterface {
    public PokerGameInterfaceImpl(List<PokerGamer> gamers) {
        this.gamers = gamers;
    }

    @Override
    public void distributeCards() {
        Command[] commands = new Command[gamers.size()];
        for (int i = 0; i < gamers.size(); ++i) {
            Card first = Card.getRandCard(cards);
            Card second = Card.getRandCard(cards);
            
            PokerGamer gamer = gamers.get(i);
            gamer.addCard(first);
            gamer.addCard(second);
            
            GetCardCommand command = new GetCardCommand();
            command.setGamer(gamer);
            commands[i] = command;
        }
        
        executor.execute(commands);
    }

    @Override
    public void requestSmallBlind(PokerGamer gamer) {
        GamerCommand command = new SmallBlindCommand();
        command.setGamer(gamer);
        executor.execute(command);
    }

    @Override
    public void requestBigBlind(PokerGamer gamer) {
        GamerCommand command = new BigBlindCommand();
        command.setGamer(gamer);
        executor.execute(command);
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
        
        // step fife - distribute cards
        distributeCards();
        
        // step six - get bets
        betRound();
        
        // step seven - pre-flop
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
    
    @Override
    public void betRound() {
        // next to big blind
        int gamerIndex = (lastButton + 3) % gamers.size();
        double maxBet = gamers.get((lastButton + 2) % gamers.size()).getBet();
        boolean wasChanged = true;
        
        // 1 2 3 4 5
        while (wasChanged) {
            
        }
    }

    @Override
    public void preFlop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void flop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void turn() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void river() {
        // TODO Auto-generated method stub
        
    }
    
    protected void requestNames() {
        for (PokerGamer gamer : gamers) {
            RequestNameCommand command = new RequestNameCommand();
            command.setGamer(gamer);
            executor.execute(command);
        }
    }
    
    private Set<Card> cards = new HashSet<Card>();
    private Executor executor = new CommandExecutor();
    private List<PokerGamer> gamers;
    private int lastButton = -1;
}
