package com.unit7.services.pokerservice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
	
	public double getBetValue() {
        return betValue;
    }

    public void setBetValue(double betValue) {
        this.betValue = betValue;
    }

    public Set<Card> getDeck() {
        return deck;
    }
    
    public void addCard(Card card) {
        deck.add(card);
    }

    public void addToBank(double value) {
        bank += value;
    }
    
    public void resetBank() {
        bank = 0;
    }
    
    public double getBank() {
        return bank;
    }
    
    public List<PokerGamer> getGamers() {
        return gamers;
    }
    
    private Map<Integer, Card> cards = new HashMap<Integer, Card>();
	private List<PokerGamer> gamers = new ArrayList<PokerGamer>();
	private Card[] tableCards = new Card[CARDS_PRIKUP];
	
	private double bank;
	
	public static final int CARDS_COUNT = 52;
	public static final int CARDS_PRIKUP = 5;
	
	private double betValue = 1; 
	
	/**
	 * Уже отданные карты
	 */
	private Set<Card> deck = new HashSet<Card>();
	
	private static final Logger log = Logger.getLogger(PokerModel.class);
}
