package com.unit7.study.cryptography.labs.lab4.forms;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.unit7.study.cryptography.labs.lab4.MentalPoker;

public class GameTable extends AbstractForm {
	public GameTable(int n) {
		this.gamersCount = n;
		this.poker = new MentalPoker(n);
	}
	
	@Override
	public JFrame createGUI() {
		JPanel content = new JPanel();
		JPanel table = new JPanel(new GridLayout(5, 5, 20, 20));
		
		content.add(table);
		getContentPane().add(content);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		return this;
	}

	private int gamersCount;
	private MentalPoker poker;
}
