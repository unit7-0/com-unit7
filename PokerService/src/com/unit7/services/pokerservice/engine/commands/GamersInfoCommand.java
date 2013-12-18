/**
 * Date:	18 дек. 2013 г.
 * File:	GamersInfoCommand.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.engine.commands;

import com.unit7.services.pokerservice.engine.framework.Controller;

/**
 * @author unit7
 *
 */
public class GamersInfoCommand extends AbstractCommand {
    public GamersInfoCommand() {
        setCommandType(CommandType.GAMERS_INFO);
    }
    
    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.engine.commands.Command#execute(com.unit7.services.pokerservice.engine.framework.Controller)
     */
    @Override
    public void execute(Controller controller) {
        controller.execute(this);
    }

}
