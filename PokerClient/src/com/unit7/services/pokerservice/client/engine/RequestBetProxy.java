/**
 * Date:	22 дек. 2013 г.
 * File:	RequestBetProxy.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

import com.unit7.services.pokerservice.client.app.gui.MainForm;
import com.unit7.services.pokerservice.client.commands.containers.CommandContainerType;

/**
 * @author unit7
 *
 */
public class RequestBetProxy implements Proxy<Object, CommandContainerType> {
    public RequestBetProxy(MainForm form) {
        this.form = form;
    }
    
    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.engine.Proxy#request(java.lang.Object)
     */
    @Override
    public CommandContainerType request(Object obj) {
        return form.getBet();
    }

    private MainForm form;
}
