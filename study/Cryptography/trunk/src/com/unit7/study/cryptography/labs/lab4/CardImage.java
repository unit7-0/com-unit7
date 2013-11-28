package com.unit7.study.cryptography.labs.lab4;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class CardImage extends JPanel {
	public CardImage(BufferedImage image) {
		this.image = image;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	}

	private BufferedImage image;
}
