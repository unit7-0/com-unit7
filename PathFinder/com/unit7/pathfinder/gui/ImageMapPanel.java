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
import java.util.Map;

import javax.swing.JPanel;

import com.unit7.pathfinder.engine.Holder;
import com.unit7.pathfinder.engine.ImageMap;
import com.unit7.pathfinder.graphs.Edge;
import com.unit7.pathfinder.graphs.Node;
import com.unit7.pathfinder.graphs.Pair;
import com.unit7.pathfinder.tools.Utils;

/**
 * @author unit7
 * 
 */
public class ImageMapPanel extends JPanel {
	private static final long serialVersionUID = -5683310778120771667L;

	public ImageMapPanel(Holder<ImageMap> mapHolder) {
		this.mapHolder = mapHolder;
		mapHolder.getObj().setImagePanel(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Image image = mapHolder.getObj().getImage();
		if (image != null)
			g.drawImage(image, 0, 0, image.getWidth(null),
					image.getHeight(null), null);

		g.setColor(pointColor);
		Map<Pair<Integer, Integer>, Node> points = mapHolder.getObj()
				.getPoints();
		Pair<Integer, Integer>[] selected = mapHolder.getObj()
				.getSelectedPoints();
		for (Iterator<Map.Entry<Pair<Integer, Integer>, Node>> it = points.entrySet().iterator(); it
				.hasNext();) {
			Map.Entry<Pair<Integer, Integer>, Node> p = it.next();
			// рисуем связи
			Node node = p.getValue();
			int x = p.getKey().getFirst(), y = p.getKey().getSecond();
			if (node != null) {
				for (Edge e : node.getEdges()) {
					Node n1 = e.getDestination();
					Pair<Integer, Integer> to = mapHolder.getObj()
							.findPoint(n1);
					g.drawLine(x, y, to.getFirst(),
							to.getSecond());
				}
			}

			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.drawString(node.getName(), x, y - radius);
			g.setColor(c);
			if (!Utils.isIntersect(p.getKey(), selected[0], radius)
					&& !Utils.isIntersect(p.getKey(), selected[1], radius)) {
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

	public void setMapHolder(Holder<ImageMap> mapHolder) {
		this.mapHolder = mapHolder;
	}

	private Holder<ImageMap> mapHolder;
	private int radius = ImageMap.POINT_RADIUS;
	private Color pointColor = Color.BLUE;
	private Color selectedPointColor = Color.RED;
}
