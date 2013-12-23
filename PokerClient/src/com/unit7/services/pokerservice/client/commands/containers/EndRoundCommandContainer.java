package com.unit7.services.pokerservice.client.commands.containers;

public class EndRoundCommandContainer extends AbstractCommandContainer {
	private static final long serialVersionUID = -8636035520396595290L;

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	private double money;
}
