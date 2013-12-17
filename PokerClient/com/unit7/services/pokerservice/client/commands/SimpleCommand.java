/**
 * Date:	17 дек. 2013 г.
 * File:	SimpleCommand.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.commands;

import com.unit7.services.pokerservice.client.engine.Controller;

/**
 * @author unit7
 *
 */
public class SimpleCommand implements Command {

    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.commands.Command#execute(com.unit7.services.pokerservice.client.engine.Controller)
     */
    @Override
    public void execute(Controller controller) {    
        controller.execute(this);
    }

    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.commands.Command#getType()
     */
    @Override
    public CommandType getType() {
        return type;
    }

    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.commands.Command#setType(com.unit7.services.pokerservice.client.commands.CommandType)
     */
    @Override
    public void setType(CommandType type) {
        this.type = type;
    }

    private CommandType type;
}
