package com.unit7.services.pokerservice.client.commands.containers;

/**
 * Нужен только тип хода - call, raise, fold
 * 
 * @author unit7
 * 
 */
public class RequestBetContainer extends AbstractCommandContainer {
	private static final long serialVersionUID = -2138226723548420022L;

	public double getMaxBet() {
		return maxBet;
	}

	public void setMaxBet(double maxBet) {
		this.maxBet = maxBet;
	}

	private double maxBet;
}
