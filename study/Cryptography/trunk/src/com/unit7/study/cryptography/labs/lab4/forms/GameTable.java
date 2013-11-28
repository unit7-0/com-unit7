package com.unit7.study.cryptography.labs.lab4.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.unit7.study.cryptography.labs.lab4.Card;
import com.unit7.study.cryptography.labs.lab4.MentalPoker;

public class GameTable extends AbstractForm {
	public GameTable(int n) {
		this.gamersCount = n;
		this.poker = new MentalPoker(n);
	}
	
	@Override
	public JFrame createGUI() {
		JPanel content = new JPanel();
		JPanel table = new JPanel(new GridBagLayout());
		JPanel tableCards = new JPanel(new GridLayout(1, 5, 5, 5));
		JPanel[] gamerCards = new JPanel[10];
		for (int i = 0; i < gamerCards.length; ++i) {
			gamerCards[i] = new JPanel(new GridLayout(1, 2, 5, 5));
		}
		
		GridBagConstraints c = new GridBagConstraints();
		
		// прикуп
		c.gridx = 2;
		c.gridy = 2;
		table.add(tableCards, c);
		
		// игроки от верхнего левого угла, по кругу
		c.gridx = 1;
		c.gridy = 0;
		table.add(gamerCards[0], c);
		
		c.gridx = 2;
		c.gridy = 0;
		table.add(gamerCards[0], c);
		
		c.gridx = 3;
		c.gridy = 0;
		table.add(gamerCards[0], c);
		
		c.gridx = 4;
		c.gridy = 1;
		table.add(gamerCards[0], c);
		
		c.gridx = 4;
		c.gridy = 3;
		table.add(gamerCards[0], c);
		
		c.gridx = 3;
		c.gridy = 3;
		table.add(gamerCards[0], c);
		
		c.gridx = 3;
		c.gridy = 2;
		table.add(gamerCards[0], c);
		
		c.gridx = 3;
		c.gridy = 1;
		table.add(gamerCards[0], c);
		
		c.gridx = 0;
		c.gridy = 1;
		table.add(gamerCards[0], c);
		
		c.gridx = 0;
		c.gridy = 2;
		table.add(gamerCards[0], c);
		
		content.add(table);
		getContentPane().add(content);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		return this;
	}

	private void loadImages() {
		for (String name : cardNames) {
			try {
				BufferedImage image = ImageIO.read(new File(imagesPath + "/" + name));
				Card card = Card.valueOf(name);
				cardImages.put(card, image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private int gamersCount;
	private MentalPoker poker;
	private String imagesPath = "";
	private String[] cardNames = {  };
	private Map<Card, BufferedImage> cardImages = new HashMap();
}
