package com.unit7.services.pokerservice.engine;

import java.util.Map;

import com.unit7.services.pokerservice.engine.commands.Command;
import com.unit7.services.pokerservice.engine.commands.GamerCommand;
import com.unit7.services.pokerservice.engine.framework.Subject;
import com.unit7.services.pokerservice.model.PokerGamer;

public interface PokerGameInterface extends Subject {
    PokerGamer selectButton();

    void distributeCards();

    void requestSmallBlind(PokerGamer gamer);

    void requestBigBlind(PokerGamer gamer);

    void betRound();

    void preFlop();

    void flop();

    void turn();

    void river();

    void game();
    
    void determineWinner();
}
