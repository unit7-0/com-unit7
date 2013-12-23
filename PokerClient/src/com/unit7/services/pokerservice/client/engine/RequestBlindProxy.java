/**
 * Date:	23 дек. 2013 г.:17:08:14
 * File:	RequestBlindProxy.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.engine;

import com.unit7.services.pokerservice.client.app.gui.MainForm;

public class RequestBlindProxy implements Proxy<String, Object> {
	public RequestBlindProxy(MainForm form) {
		this.form = form;
	}
	
	/* (non-Javadoc)
	 * @see com.unit7.services.pokerservice.client.engine.Proxy#request(java.lang.Object)
	 */
	@Override
	public Object request(String data) {
		form.getBlind(data);
		return null;
	}

	private MainForm form;
}
