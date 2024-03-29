package com.unit7.services.pokerservice.client.commands.containers;

import java.util.List;

import com.unit7.services.pokerservice.client.model.Card;

public class CardContainer extends AbstractCommandContainer {
    private static final long serialVersionUID = 3851122433840756170L;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getGamerId() {
        return gamerId;
    }

    public void setGamerId(int gamerId) {
        this.gamerId = gamerId;
    }

    List<Card> cards;
    private int gamerId;
}
