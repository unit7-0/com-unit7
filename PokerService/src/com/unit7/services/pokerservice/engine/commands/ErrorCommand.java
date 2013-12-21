/**
 * Date:	21 дек. 2013 г.
 * File:	ErrorCommand.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.engine.commands;

import java.net.Socket;

import com.unit7.services.pokerservice.engine.framework.Controller;

/**
 * @author unit7
 *
 */
public class ErrorCommand extends AbstractCommand {
    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.engine.commands.Command#execute(com.unit7.services.pokerservice.engine.framework.Controller)
     */
    @Override
    public void execute(Controller controller) {
        controller.execute(this);
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorCommand() {
        setCommandType(CommandType.ERROR);
    }
    
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private String message;
    private Socket socket;
}
