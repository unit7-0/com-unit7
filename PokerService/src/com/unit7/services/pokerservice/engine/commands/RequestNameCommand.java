package com.unit7.services.pokerservice.engine.commands;

/**
 * Нужен только игрок для запроса имени
 * @author unit7
 *
 */
public class RequestNameCommand extends GamerCommand {
    public RequestNameCommand() {
        setCommandType(CommandType.REQUEST_NAME);
    }
}
