package com.unit7.study.cryptography.labs.lab4;

import java.util.Random;

public class Card {
    public Card(int type) {
        setType(type);
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
    
    public static Card randCard() {
        return new Card(rand.nextInt(deck.length)); 
    }
    
    public void setType(int type) {
        if (type < 0 || type >= deck.length)
            throw new IllegalArgumentException("type");
        
        this.type = type;
    }
    
    public int getType() {
        return type;
    }
    
    public String getValueType() {
        return deck[type];
    }
    
    private static final String[] deck = { "2♠", "2♥", "2♣", "2♦", "3♠", "3♥", "3♣", "3♦", "4♠", "4♥", "4♣", "4♦",
        "5♠", "5♥", "5♣", "5♦", "6♠", "6♥", "6♣", "6♦", "7♠", "7♥", "7♣", "7♦", "8♠", "8♥", "8♣", "8♦", "9♠", "9♥",
        "9♣", "9♦", "10♠", "10♥", "10♣", "10♦", "Jack♠", "Jack♥", "Jack♣", "Jack♦", "Queen♠", "Queen♥", "Queen♣",
        "Queen♦", "King♠", "King♥", "King♣", "King♦", "Ace♠", "Ace♥", "Ace♣", "Ace♦" };
    
    @Override
    public String toString() {
        return deck[type];
    }
    
    private int type;
    private static Random rand = new Random();
}
