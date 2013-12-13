/**
 * Date:	13.12.2013:18:25:46
 * File:	MainFormStub.java
 * 
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.commands.app.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFormStub extends JFrame implements AbstractForm {

	/* (non-Javadoc)
	 * @see com.unit7.services.pokerservice.client.commands.app.gui.AbstractForm#createGUI()
	 */
	@Override
	public JFrame createGUI() {
		JPanel con = new JPanel();
		JButton but = new JButton("connect");
		
		but.addActionListener(l);
		
		con.add(but);
		getContentPane().add(con);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		return this;
	}
	
}
