package com.unit7.services.pokerservice.client.model;

import java.io.Serializable;
import java.util.Random;
import java.util.Set;

import com.unit7.services.pokerservice.client.tools.Utils;

public class Card implements Serializable {
	private static final long serialVersionUID = -501915713023218575L;

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
	        return new Card(CardType.createCardType(suits[Utils.getRandInt(len)]));
	    } else {
	        Card card = null;
	        do {
	            card = new Card(CardType.createCardType(suits[Utils.getRandInt(len)]));
	        } while (cards.contains(card));
	        
	        return card;
	    }
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	private CardType type;
}
