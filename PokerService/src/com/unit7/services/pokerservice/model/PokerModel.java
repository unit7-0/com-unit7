package com.unit7.services.pokerservice.model;

import java.net.Socket;
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
import com.unit7.services.pokerservice.client.model.Suit;

public class PokerModel {
	public PokerModel(List<Socket> clients) {
		Random random = new Random();
		for (int i = 0; i < clients.size(); ++i) {
			// FIX replace with factory
			PokerGamer gamer = new PokerGamer();
			gamer.setSocket(clients.get(i));
			gamer.setId(i);
			gamers.add(gamer);
			for (int j = 0; j < 2; ++j) {
				Card card = Card.getRandCard(deck);
				deck.add(card);
				gamer.addCard(card);
			}
		}
		
		/* for crypting, now not needed, because we not realize mental poker. Just poker with ciphering
		for (int i = 0; i < CARDS_COUNT; ++i) {
			// TODO may be replace with factory
			cards.put(random.nextInt(Integer.MAX_VALUE - 1) + 1, new Card(CardType.createCardType(Suit.rand())));
		}*/
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
    
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public double getInitialMoney() {
		return initialMoney;
	}

	public void setInitialMoney(double initialMoney) {
		this.initialMoney = initialMoney;
	}

//	private Map<Integer, Card> cards = new HashMap<Integer, Card>();
	private List<PokerGamer> gamers = new ArrayList<PokerGamer>();
	private Card[] tableCards = new Card[CARDS_PRIKUP];
	
	private double bank;
	
	public static final int CARDS_COUNT = 52;
	public static final int CARDS_PRIKUP = 5;
	
	private double betValue = 2; 
    private double initialMoney = 100;
	
	/**
	 * Уже отданные карты
	 */
	private Set<Card> deck = new HashSet<Card>();
	
	private Stage stage;
	
	private static final Logger log = Logger.getLogger(PokerModel.class);
}
