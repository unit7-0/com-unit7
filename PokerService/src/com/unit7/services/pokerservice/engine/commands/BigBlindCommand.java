package com.unit7.services.pokerservice.engine.commands;

public class BigBlindCommand extends BetCommand {
	public BigBlindCommand() {
		setCommandType(CommandType.REQUEST_BIG_BLIND);
	}
}
