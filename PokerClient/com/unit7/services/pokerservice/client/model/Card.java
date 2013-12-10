package com.unit7.services.pokerservice.client.model;

import java.util.Random;
import java.util.Set;

public class Card {
	public Card(CardType type) {
		this.type = type;
	}
	
	public CardType getType() {
		return type;
	}
	
	public static Card getRandCard(Set<Card> cards) {
	    CardType[] types = CardType.values();
	    int len = types.length;
	    
	    if (cards == null) {
	        return new Card(CardType.valueOf(types[rnd.nextInt(len)].name()));
	    } else {
	        Card card = null;
	        do {
	            card = new Card(CardType.valueOf(types[rnd.nextInt(len)].name()));
	        } while (cards.contains(card));
	        
	        return card;
	    }
	}

	private CardType type;
	private static final Random rnd = new Random(System.currentTimeMillis());
}
