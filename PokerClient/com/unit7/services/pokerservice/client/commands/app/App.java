/**
 * Date:	13.12.2013:18:24:34
 * File:	App.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.commands.app;

import javax.swing.JFrame;

import com.unit7.services.pokerservice.client.commands.app.gui.MainFormStub;

public class App {
	public static void main(String[] args) {
		JFrame app = new MainFormStub().createGUI();
		app.setVisible(true);
	}
}
