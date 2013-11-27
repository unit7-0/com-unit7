package com.unit7.study.cryptography.labs.lab4.forms;

import javax.swing.JFrame;

public class GameTable extends AbstractForm {
	public GameTable(int n) {
		this.gamersCount = n;
	}
	
	@Override
	public JFrame createGUI() {
		
		// игроки, карты
		
		return this;
	}

	private int gamersCount;
}
