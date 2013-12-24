/**
 * Date:	24 дек. 2013 г.:10:56:32
 * File:	MainFormStub.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.tests;

import com.unit7.services.pokerservice.client.app.gui.MainForm;
import com.unit7.services.pokerservice.client.commands.containers.CommandContainerType;

public class MainFormStub extends MainForm {

	@Override
	public CommandContainerType getBet() {
		return null;
	}

	@Override
	public void getBlind(String message) {
		//
	}

	@Override
	public void refresh() {
		// 
	}

	@Override
	public void roundInfo(String info) {
		// 
	}

	@Override
	public void endGame() {
		// 
	}

	@Override
	public String getUserName() {
		return null;
	}

}
