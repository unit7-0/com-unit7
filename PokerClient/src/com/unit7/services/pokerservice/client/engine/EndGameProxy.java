/**
 * Date:	22 дек. 2013 г.
 * File:	EndGameProxy.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

import com.unit7.services.pokerservice.client.app.gui.MainForm;
import com.unit7.services.pokerservice.client.resources.Resources;

/**
 * @author unit7
 *
 */
public class EndGameProxy implements Proxy {
    public EndGameProxy(MainForm form) {
        this.form = form;
    }
    
    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.engine.Proxy#request(java.lang.Object)
     */
    @Override
    public Object request(Object data) {
        form.endGame();
        return Resources.STUB;
    }

    private MainForm form;
}
