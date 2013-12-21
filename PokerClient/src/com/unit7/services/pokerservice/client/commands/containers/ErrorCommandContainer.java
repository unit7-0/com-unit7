/**
 * Date:	21 дек. 2013 г.
 * File:	ErrorCommandContainer.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.commands.containers;

/**
 * @author unit7
 *
 */
public class ErrorCommandContainer extends AbstractCommandContainer {
    private static final long serialVersionUID = 1532833787499495863L;

    public ErrorCommandContainer() {
        setType(CommandContainerType.ERROR);
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    private String message;
}
