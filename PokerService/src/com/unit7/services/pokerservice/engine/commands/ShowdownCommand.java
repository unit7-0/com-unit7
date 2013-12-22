package com.unit7.services.pokerservice.engine.commands;

import com.unit7.services.pokerservice.client.model.CombinationType;

public class ShowdownCommand extends GamerCommand {

    public ShowdownCommand() {
        this.setCommandType(CommandType.SHOWDOWN);
    }

    public CombinationType getCombinationType() {
        return combinationType;
    }

    public void setCombinationType(CombinationType combinationType) {
        this.combinationType = combinationType;
    }

    private CombinationType combinationType;
}
