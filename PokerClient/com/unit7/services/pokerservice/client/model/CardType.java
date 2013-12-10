package com.unit7.services.pokerservice.client.model;

import java.util.Random;

public enum CardType {
	TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, KNAVE, QUEEN, KING, ACE;
	
	public CardType createCardType(Suit suit) {
	    CardType type = values()[new Random().nextInt(values().length)];
	    type.suit = suit;
	    return type;
	}
	
	public Suit getSuit() {
	    return suit;
	}
	
	private Suit suit;
}
