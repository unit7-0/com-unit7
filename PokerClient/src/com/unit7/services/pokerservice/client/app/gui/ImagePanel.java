/**
 * Date:	21 дек. 2013 г.
 * File:	ImagePanel.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.app.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * @author unit7
 *
 */
public class ImagePanel extends JPanel {
    public ImagePanel(Image image) {
        this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;
        if (image != null)
            g.drawImage(image, 0, 0, null);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private Image image;
}
