package com.unit7.services.pokerservice.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.unit7.services.pokerservice.client.model.Card;
import com.unit7.services.pokerservice.client.model.CardType;

public class PokerModel {
	public PokerModel(int gamersCount) {
		Random random = new Random();
		for (int i = 0; i < gamersCount; ++i) {
			// FIX replace with factory
			gamers.add(new PokerGamer());
		}
		
		for (int i = 0; i < CARDS_COUNT; ++i) {
			// TODO may be replace with factory
			cards.put(random.nextInt(Integer.MAX_VALUE - 1) + 1, new Card(CardType.getRandType()));
		}
	}  
	
	public int getBetValue() {
        return betValue;
    }

    public void setBetValue(int betValue) {
        this.betValue = betValue;
    }

    public Set<Card> getDeck() {
        return deck;
    }
    
    public void addCard(Card card) {
        deck.add(card);
    }

    private Map<Integer, Card> cards = new HashMap<Integer, Card>();
	private Set<PokerGamer> gamers = new HashSet<PokerGamer>();
	private Card[] tableCards = new Card[CARDS_PRIKUP];
	
	private int bank;
	
	public static final int CARDS_COUNT = 52;
	public static final int CARDS_PRIKUP = 5;
	
	private int betValue = 1; 
	
	/**
	 * Уже отданные карты
	 */
	private Set<Card> deck = new HashSet<Card>();
	
	private static final Logger log = Logger.getLogger(PokerModel.class);
}
