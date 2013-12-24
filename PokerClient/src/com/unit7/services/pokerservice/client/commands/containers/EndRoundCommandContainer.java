package com.unit7.services.pokerservice.client.commands.containers;

import java.util.Map;

import com.unit7.services.pokerservice.client.model.CombinationType;

public class EndRoundCommandContainer extends AbstractCommandContainer {
	private static final long serialVersionUID = -8636035520396595290L;

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Map<Integer, CombinationType> getCombinations() {
		return combinations;
	}

	public void setCombinations(Map<Integer, CombinationType> combinations) {
		this.combinations = combinations;
	}

	private double money;
	private Map<Integer, CombinationType> combinations;
}
