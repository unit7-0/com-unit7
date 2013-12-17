package com.unit7.services.pokerservice.client.model;

import java.util.Random;
import java.util.Set;

import com.unit7.services.pokerservice.client.tools.Utils;

public class Card {
	public Card(CardType type) {
		this.type = type;
	}
	
	public CardType getType() {
		return type;
	}
	
	public static Card getRandCard(Set<Card> cards) {
	    Suit[] suits = Suit.values();
	    int len = suits.length;
	    
	    if (cards == null) {
	        return new Card(CardType.valueOf(suits[Utils.getRandInt(len)].name()));
	    } else {
	        Card card = null;
	        do {
	            card = new Card(CardType.createCardType(suits[Utils.getRandInt(suits.length)]));
	        } while (cards.contains(card));
	        
	        return card;
	    }
	}

	private CardType type;
}