package com.unit7.services.pokerservice.engine.commands;


public class SmallBlindCommand extends BetCommand {
    public SmallBlindCommand() {
        setCommandType(CommandType.REQUEST_SMALL_BLIND);
    }
}
