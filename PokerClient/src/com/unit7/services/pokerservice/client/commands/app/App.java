/**
 * Date:	13.12.2013:18:24:34
 * File:	App.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.commands.app;

import javax.swing.JFrame;

import com.unit7.services.pokerservice.client.app.gui.MainForm;

public class App {
	public static void main(String[] args) {
		JFrame app = new MainForm().createGUI();
		app.setVisible(true);
	}
}
