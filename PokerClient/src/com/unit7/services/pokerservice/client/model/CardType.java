package com.unit7.services.pokerservice.client.model;

import java.io.Serializable;

import com.unit7.services.pokerservice.client.tools.Utils;

public class CardType implements Serializable {
    private static final long serialVersionUID = 8042822672636516787L;
    
    public static final CardType ACE = new CardType("ace"), TWO = new CardType("two"), THREE = new CardType("three"), FOUR = new CardType("four"),
    FIVE = new CardType("five"), SIX = new CardType("six"), SEVEN = new CardType("saven"), EIGHT = new CardType("eight"), 
    NINE = new CardType("nine"), TEN = new CardType("ten"), KNAVE = new CardType("knave"), QUEEN = new CardType("queen"), 
    KING = new CardType("king"), UNKNOWN = new CardType("unknown");
	
    public static CardType[] values() {
        return values;
    }
    
    public int ordinal() {
        for (int i = 0; i < values.length; ++i) {
            if (values[i].name.equals(this.name))
                return i;
        }
        
        return -1;
    }
    
    private static final CardType[] values = new CardType[] { ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, KNAVE, QUEEN, KING, UNKNOWN };
    
	private CardType(String name) {
	    this.name = name;
	}
	
	public static CardType createCardType(Suit suit) {
	    CardType type = values()[Utils.getRandInt(values().length)];
	    type = new CardType(type.getName());
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
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
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
        CardType other = (CardType) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (suit != other.suit)
            return false;
        return true;
    }

    private String name;
	private Suit suit;
}
