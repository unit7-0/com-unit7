package com.unit7.services.pokerservice.client.commands.containers;

/**
 * Нужен только тип хода - call, raise, fold
 * 
 * @author unit7
 * 
 */
public class RequestBetContainer extends AbstractCommandContainer {
	private static final long serialVersionUID = -2138226723548420022L;

	public double getBet() {
		return bet;
	}

	public void setBet(double bet) {
		this.bet = bet;
	}

	private double bet;
}
