package com.unit7.services.pokerservice.client.commands.containers;

import java.util.Map;

import com.unit7.services.pokerservice.client.model.CombinationType;
import com.unit7.services.pokerservice.client.model.LightweightGamer;

public class EndRoundCommandContainer extends AbstractCommandContainer {
	private static final long serialVersionUID = -8636035520396595290L;

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Map<LightweightGamer, CombinationType> getCombinations() {
        return combinations;
    }

    public void setCombinations(Map<LightweightGamer, CombinationType> combinations) {
        this.combinations = combinations;
    }

    private double money;
	private Map<LightweightGamer, CombinationType> combinations;
}
