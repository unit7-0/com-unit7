package com.unit7.services.pokerservice.client.model;

import com.unit7.services.pokerservice.client.tools.Utils;

public enum CardType {
	TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, KNAVE, QUEEN, KING, ACE, UNKNOWN;
	
	public static CardType createCardType(Suit suit) {
	    CardType type = values()[Utils.getRandInt(values().length)];
	    type.suit = suit;
	    return type;
	}
	
	public Suit getSuit() {
	    return suit;
	}
	
	public String getName() {
	    // stub
	    return null;
	}
	
	private Suit suit;
}
