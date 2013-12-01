package com.unit7.services.pokerservice.engine;

import com.unit7.services.pokerservice.model.PokerGamer;

public interface PokerGameInterface {
    PokerGamer selectButton();
    void distributeCards();
    void requestSmallBlind(PokerGamer gamer);
    void requestBigBlind(PokerGamer gamer);
    void game();
}
