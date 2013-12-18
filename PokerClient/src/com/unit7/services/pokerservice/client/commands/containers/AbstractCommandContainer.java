package com.unit7.services.pokerservice.client.commands.containers;

public abstract class AbstractCommandContainer implements CommandContainer {
    private static final long serialVersionUID = 8177804514148161001L;

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
