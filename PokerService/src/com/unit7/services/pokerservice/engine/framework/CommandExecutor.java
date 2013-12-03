package com.unit7.services.pokerservice.engine.framework;

import com.unit7.services.pokerservice.engine.commands.Command;

public class CommandExecutor implements Executor {
    @Override
    public void execute(Object... args) {
        if (args == null)
            return;
        
        if (!(args instanceof Command[]))
            throw new IllegalArgumentException("args must be Command type");
        
        Command[] commands = (Command[]) args;
        for (Command command : commands) {
            command.execute(Controller.getInstance());
        }
    }

}
