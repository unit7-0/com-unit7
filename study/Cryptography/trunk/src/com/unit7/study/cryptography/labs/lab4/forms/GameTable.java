package com.unit7.study.cryptography.labs.lab4.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.unit7.study.cryptography.labs.lab4.Card;
import com.unit7.study.cryptography.labs.lab4.CardImage;
import com.unit7.study.cryptography.labs.lab4.MentalPoker;

public class GameTable extends AbstractForm {
	public GameTable(int n) {
		this.gamersCount = n;
		this.poker = new MentalPoker(n);
	}
	
	@Override
	public JFrame createGUI() {
		loadImages();
		
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
		table.add(gamerCards[1], c);
		
		c.gridx = 3;
		c.gridy = 0;
		table.add(gamerCards[2], c);
		
		c.gridx = 4;
		c.gridy = 1;
		table.add(gamerCards[3], c);
		
		c.gridx = 4;
		c.gridy = 3;
		table.add(gamerCards[4], c);
		
		c.gridx = 3;
		c.gridy = 3;
		table.add(gamerCards[5], c);
		
		c.gridx = 3;
		c.gridy = 2;
		table.add(gamerCards[6], c);
		
		c.gridx = 3;
		c.gridy = 1;
		table.add(gamerCards[7], c);
		
		c.gridx = 0;
		c.gridy = 1;
		table.add(gamerCards[8], c);
		
		c.gridx = 0;
		c.gridy = 2;
		table.add(gamerCards[9], c);
		
		int k = 0;
		for (int i = 0; i < gamersCount; ++i) {
			List<Card> cards = poker.getGamers().get(i).getCards();
			for (int j = 0; j < cards.size(); ++j) {
				Card card = cards.get(j);
				JPanel panel = new CardImage(cardImages.get(card));
				gamerCards[k].add(panel);
				
				k = (k + 2) % gamerCards.length;
				if (k == 0)
					k += 1;
			}
		}
		
		for (int i = 0; i < 5; ++i) {
			List<Card> cards = poker.getRemainCards();
			Card card = cards.get(i);
			tableCards.add(new CardImage(cardImages.get(card)));
		}
		
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
