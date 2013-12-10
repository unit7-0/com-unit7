package com.unit7.services.pokerservice.client.commands;

public abstract class AbstractCommandContainer implements CommandContainer {

    @Override
    public void setType(CommandContainerType type) {
        this.type = type;
    }

    @Override
    public CommandContainerType getType() {
        return type;
    }

    private CommandContainerType type;
}
