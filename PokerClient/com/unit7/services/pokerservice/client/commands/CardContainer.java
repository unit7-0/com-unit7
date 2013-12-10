package com.unit7.services.pokerservice.client.commands;

import java.util.List;

import com.unit7.services.pokerservice.client.model.Card;

public class CardContainer extends AbstractCommandContainer {
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    List<Card> cards;
}
