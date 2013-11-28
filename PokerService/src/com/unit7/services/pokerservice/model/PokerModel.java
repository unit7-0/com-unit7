package com.unit7.services.pokerservice.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PokerModel {
	public PokerModel(int gamersCount) {
		Random random = new Random();
		for (int i = 0; i < gamersCount; ++i) {
			// FIX replace with factory
			gamersCards.put(new PokerGamer(), new Card[2]);
		}
		
		for (int i = 0; i < CARDS_COUNT; ++i) {
			// TODO may be replace with factory
			cards.put(random.nextInt(Integer.MAX_VALUE - 1) + 1, new Card(CardType.getRandType()));
		}
	}  
	
	private Map<Integer, Card> cards = new HashMap<Integer, Card>();
	private Map<PokerGamer, Card[]> gamersCards = new HashMap<PokerGamer, Card[]>();
	private Card[] tableCards = new Card[CARDS_PRIKUP];
	
	private int bank;
	
	public static final int CARDS_COUNT = 52;
	public static final int CARDS_PRIKUP = 5;
}
