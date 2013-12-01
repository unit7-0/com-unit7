package com.unit7.services.pokerservice.engine.commands;

public abstract class AbstractCommand implements Command {
    @Override
    public CommandType getCommandType() {
        return type;
    }

    @Override
    public void setCommandType(CommandType type) {
        this.type = type;
    }

    private CommandType type;
}
