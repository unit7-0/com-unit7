/**
 * Date:	21 дек. 2013 г.
 * File:	GamerPanel.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.app.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

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

        if (log.isDebugEnabled()) {
            log.debug("[\tReceived gamer: " + gamer.getName() + "\t]");
        }

        JPanel info = new JPanel();
        JPanel cards = new JPanel();

        JPanel infoPanel = new JPanel();
        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel name = new JLabel("Ник:\t" + gamer.getName());
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
        info.setBackground(Color.BLUE);
        setBackground(Color.RED);
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
            Image image = Resources.getImageByName(name1);
            firstCard.setImage(image);
            if (image != null) {
                firstCard.setSize(image.getWidth(null), image.getHeight(null));
                Container parent = firstCard.getParent();
                while (parent.getParent() != null) {
                    parent = parent.getParent();
                }
                
                parent.revalidate();
            }
            
            image = Resources.getImageByName(name2);
            secondCard.setImage(image);
            if (image != null) {
                secondCard.setSize(image.getWidth(null), image.getHeight(null));
                Container parent = firstCard.getParent();
                while (parent.getParent() != null) {
                    parent = parent.getParent();
                }
                
                if (parent instanceof JFrame) 
                    ((JFrame) parent).pack();
                        
                parent.revalidate();
            }
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        firstCard.repaint();
        secondCard.repaint();
        
        bet.setText("Ставка:\t" + String.valueOf(gamer.getBet()));
        money.setText("Сердства:\t" + String.valueOf(gamer.getMoney()));
        
        if (log.isDebugEnabled()) {
            log.debug("[\tbet: " + bet.getText() + "\t]");
        }
    }

    private JLabel bet, money;
    private ImagePanel firstCard, secondCard;
    private LightweightGamer gamer;

    private static final Logger log = Logger.getLogger(GamerPanel.class);
}
