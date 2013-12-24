/**
 * Date:	21 дек. 2013 г.
 * File:	PrikupPanel.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.app.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.JPanel;

import com.unit7.services.pokerservice.client.exceptions.ResourceNotFoundException;
import com.unit7.services.pokerservice.client.model.Card;
import com.unit7.services.pokerservice.client.model.CardType;
import com.unit7.services.pokerservice.client.resources.Resources;

/**
 * @author unit7
 *
 */
public class PrikupPanel extends JPanel {
    public PrikupPanel() {
        for (int i = 0; i < panels.length; ++i) {
            panels[i] = new ImagePanel(null);
            add(panels[i]);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String[] names = new String[panels.length];
        if (prikup == null || prikup.size() == 0) {
            for (int i = 0; i < names.length; ++i)
                names[i] = CardType.UNKNOWN.getName();
        } else {
            for (int i = 0; i < prikup.size(); ++i) {
                names[i] = prikup.get(i).getType().getName();
            }
        }
        
        for (int i = 0; i < names.length; ++i) {
        	Image image = null;
            try {
            	image = Resources.getImageByName(names[i]);
                panels[i].setImage(image);
            } catch (ResourceNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            if (image != null) {
            	int width = image.getWidth(null);
            	int height = image.getHeight(null);
            	panels[i].setPreferredSize(new Dimension(width, height));
            }
            panels[i].repaint();
        }
    }

    public void setPrikup(List<Card> prikup) {
        this.prikup = prikup;
    }

    private List<Card> prikup;
    private ImagePanel[] panels = new ImagePanel[5];
}
