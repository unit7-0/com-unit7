package com.unit7.services.pokerservice.model;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.unit7.services.pokerservice.client.model.Card;

public class PokerGamer extends User implements Net {
    
    public void addCard(Card card) {
        cards.add(card);
    }
    
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Socket getSocket() {
        return socket;
    }
    
    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void addMoney(double money) {
        this.money += money;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Socket socket;
    private double bet;
    private double money;
    private int id;
    private boolean inGame = true;
    private List<Card> cards = new ArrayList<Card>();
}
