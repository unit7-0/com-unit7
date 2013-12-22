/**
 * Date:	22 дек. 2013 г.
 * File:	RequestBetProxy.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

import com.unit7.services.pokerservice.client.app.gui.MainForm;

/**
 * @author unit7
 *
 */
public class RequestBetProxy implements Proxy<String, Double> {
    public RequestBetProxy(MainForm form) {
        this.form = form;
    }
    
    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.engine.Proxy#request(java.lang.Object)
     */
    @Override
    public Double request(String data) {
        return form.getBet(data);
    }

    private MainForm form;
}
