/**
 * Date:	21 дек. 2013 г.
 * File:	GamerPanel.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.app.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.unit7.services.pokerservice.client.exceptions.ResourceNotFoundException;
import com.unit7.services.pokerservice.client.model.CardType;
import com.unit7.services.pokerservice.client.model.LightweightGamer;
import com.unit7.services.pokerservice.client.resources.Resources;

/**
 * @author unit7
 *
 */
public class GamerPanel extends JPanel {
    public GamerPanel(LightweightGamer gamer) {
        this.gamer = gamer;
        
        JPanel info = new JPanel();
        JPanel cards = new JPanel();
        
        JPanel infoPanel = new JPanel();
        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel name = new JLabel();
        bet = new JLabel();
        money = new JLabel();
        
        infoPanel.add(name);
        infoPanel.add(bet);
        infoPanel.add(money);
        
        firstCard = new ImagePanel(null);
        secondCard = new ImagePanel(null);
        
        cardsPanel.add(firstCard);
        cardsPanel.add(secondCard);
        info.add(infoPanel);
        cards.add(cardsPanel);
        
        setLayout(new GridLayout(1, 2, 10, 10));
        add(info);
        add(cards);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;
        String name1 = null;
        String name2 = null;
        if (gamer.getCards() == null || gamer.getCards().size() != 2) {
            name1 = CardType.UNKNOWN.getName();
            name2 = name1;
        } else {
            name1 = gamer.getCards().get(0).getType().getName();
            name2 = gamer.getCards().get(1).getType().getName();
        }
        
        try {
            firstCard.setImage(Resources.getImageByName(name1));
            secondCard.setImage(Resources.getImageByName(name2));
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        firstCard.repaint();
        secondCard.repaint();
    }

    private JLabel bet, money;
    private ImagePanel firstCard, secondCard;
    private LightweightGamer gamer;
}
