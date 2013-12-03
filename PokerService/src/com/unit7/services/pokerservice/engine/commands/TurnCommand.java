package com.unit7.services.pokerservice.engine.commands;

import com.unit7.services.pokerservice.engine.framework.Controller;

public class TurnCommand extends AbstractCommand {
    public TurnCommand() {
        setCommandType(CommandType.TURN);
    }
    
    @Override
    public void execute(Controller controller) {
        controller.execute(this);
    }

}
