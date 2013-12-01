package com.unit7.services.pokerservice.engine.commands;

import com.unit7.services.pokerservice.engine.Controller;

public interface Command {
    void execute(Controller controller);

    CommandType getCommandType();

    void setCommandType(CommandType type);
}
