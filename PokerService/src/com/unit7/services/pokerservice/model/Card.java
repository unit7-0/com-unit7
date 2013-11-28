package com.unit7.services.pokerservice.model;

public class Card {
	public Card(CardType type) {
		this.type = type;
	}
	
	public CardType getType() {
		return type;
	}

	private CardType type;
}
