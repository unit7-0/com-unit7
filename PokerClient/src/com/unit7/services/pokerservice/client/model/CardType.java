package com.unit7.services.pokerservice.client.model;

import com.unit7.services.pokerservice.client.tools.Utils;

public enum CardType {
    ACE("ace"), TWO("two"), THREE("three"), FOUR("four"),
    FIVE("five"), SIX("six"), SEVEN("saven"), EIGHT("eight"), 
    NINE("nine"), TEN("ten"), KNAVE("knave"), QUEEN("queen"), 
    KING("king"), UNKNOWN("unknown");
	
	private CardType(String name) {
	    this.name = name;
	}
	
	public static CardType createCardType(Suit suit) {
	    CardType type = values()[Utils.getRandInt(values().length)];
	    type.suit = suit;
	    return type;
	}
	
	public Suit getSuit() {
	    return suit;
	}
	
	public String getName() {
	    if (suit == null)
	        return name;
	    return name + "_" + suit.getName();
	}
	
	private String name;
	private Suit suit;
}
