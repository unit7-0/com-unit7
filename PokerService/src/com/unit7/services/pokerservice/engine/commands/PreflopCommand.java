package com.unit7.services.pokerservice.engine.commands;

import com.unit7.services.pokerservice.engine.framework.Controller;

public class PreflopCommand extends AbstractCommand {
    public PreflopCommand() {
        setCommandType(CommandType.PREFLOP);
    }
    
    @Override
    public void execute(Controller controller) {
        controller.execute(this);
    }

}
