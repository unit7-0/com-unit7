/**
 * Date:	17 дек. 2013 г.
 * File:	Command.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.commands;

import com.unit7.services.pokerservice.client.engine.Controller;

/**
 * @author unit7
 * 
 */
public interface Command {
    void execute(Controller controller); 
    
    CommandType getType();

    void setType(CommandType type);
}
