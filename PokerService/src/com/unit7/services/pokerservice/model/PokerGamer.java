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
    
    private List<Card> cards = new ArrayList<Card>();

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

    private Socket socket;
    private double bet;
}
