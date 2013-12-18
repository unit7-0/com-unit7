/**
 * Date:	17 дек. 2013 г.
 * File:	LightweightGamer.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author unit7
 * 
 */
public class LightweightGamer implements Serializable {
	private static final long serialVersionUID = 186996730345622387L;
	
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    private int id;
    private String name;
    private List<Card> cards;
    private double bet;
}
