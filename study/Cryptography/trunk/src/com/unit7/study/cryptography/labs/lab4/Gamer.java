package com.unit7.study.cryptography.labs.lab4;

import java.util.ArrayList;
import java.util.List;

public class Gamer extends User {

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName();
    }

    private List<Card> cards = new ArrayList<Card>();
}
