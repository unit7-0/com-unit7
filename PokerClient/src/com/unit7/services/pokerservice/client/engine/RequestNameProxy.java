/**
 * Date:	17 дек. 2013 г.
 * File:	RequestNameProxy.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

import com.unit7.services.pokerservice.client.app.gui.MainForm;

/**
 * @author unit7
 *
 */
public class RequestNameProxy implements Proxy<Object, String> {
    public RequestNameProxy(MainForm form) {
        this.form = form;
    }
    
    /* (non-Javadoc)
     * @see com.unit7.services.pokerservice.client.engine.Proxy#request(java.lang.Object)
     */
    @Override
    public String request(Object data) {
        return form.getName();
    }

    private MainForm form;
}
