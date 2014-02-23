/**
 * Date:	23 февр. 2014 г.
 * File:	ImageMapPanel.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JPanel;

import com.unit7.pathfinder.graphs.Pair;
import com.unit7.pathfinder.tools.Utils;

/**
 * @author unit7
 * 
 */
public class ImageMapPanel extends JPanel {
    public ImageMapPanel(ImageMap map) {
        this.map = map;
        map.setImagePanel(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        Image image = map.getImage();
        if (image != null)
            g.drawImage(image, 0, 0, image.getHeight(null), image.getHeight(null), null);

        g.setColor(pointColor);
        Collection<Pair<Integer, Integer>> points = map.getPoints();
        Pair<Integer, Integer>[] selected = map.getSelectedPoints();
        for (Iterator<Pair<Integer, Integer>> it = points.iterator(); it.hasNext();) {
            Pair<Integer, Integer> p = it.next();
            if (!Utils.isIntersect(p, selected[0], radius) && !Utils.isIntersect(p, selected[1], radius)) {
                int x = p.getFirst();
                int y = p.getSecond();
                g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            }
        }

        g.setColor(selectedPointColor);
        for (int i = 0; i < 2; ++i) {
            if (selected[i] != null) {
                int x = selected[i].getFirst();
                int y = selected[i].getSecond();
                g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            }
        }
    }

    private ImageMap map;
    private int radius = ImageMap.POINT_RADIUS;
    private Color pointColor = Color.BLUE;
    private Color selectedPointColor = Color.RED;
}
