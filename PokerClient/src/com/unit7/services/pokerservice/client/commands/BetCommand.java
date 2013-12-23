/**
 * Date:	23 дек. 2013 г.:8:37:53
 * File:	BetCommand.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.commands;

public class BetCommand extends SimpleCommand {
	public BetCommand() {
		setType(CommandType.BET);
	}
	
	public double getBet() {
		return bet;
	}

	public void setBet(double bet) {
		this.bet = bet;
	}

	private double bet;
}
