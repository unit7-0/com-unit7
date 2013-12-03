package com.unit7.services.pokerservice.engine.commands;

import com.unit7.services.pokerservice.engine.framework.Controller;

public class RiverCommand extends AbstractCommand {
    public RiverCommand() {
        setCommandType(CommandType.RIVER);
    }
    
    @Override
    public void execute(Controller controller) {
        controller.execute(this);
    }

}
