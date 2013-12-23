/**
 * Date:	23 дек. 2013 г.:8:37:53
 * File:	BetCommand.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.commands;

import com.unit7.services.pokerservice.client.commands.containers.CommandContainerType;

public class BetCommand extends SimpleCommand {
	public BetCommand() {
		setType(CommandType.BET);
	}

	public CommandContainerType getBetType() {
		return betType;
	}

	public void setBetType(CommandContainerType betType) {
		this.betType = betType;
	}

	// оставим это рефакторингу, пока пофиг
	private CommandContainerType betType;
}
