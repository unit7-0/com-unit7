/**
 * Date:	08 янв. 2014 г.
 * File:	GLCanvasMouseListener.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import org.apache.log4j.Logger;

/**
 * @author unit7
 * 
 */
public class GLCanvasMouseListener extends MouseAdapter {
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double v = e.getPreciseWheelRotation();
		if (v > 0)
			Camera.getInstance()
					.setRatio(Camera.getInstance().getRatio() / 1.3);
		else
			Camera.getInstance()
					.setRatio(Camera.getInstance().getRatio() * 1.3);
		/*
		 * double eye = Camera.getInstance().getEyeZ(); if ((eye >= 0 && eye + v
		 * <= 0) || (eye <= 0 && eye + v >= 0)) {
		 * Camera.getInstance().setRatio(Camera.getInstance().getRatio() * 1.3);
		 * } else { Camera.getInstance().addEyeZ(v); }
		 */

		if (log.isDebugEnabled()) {
			log.debug("Mouse wheeled: " + v);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		double del = 5;
		double diffX = (lastX - x) / del;
		double diffY = (lastY - y) / del;

		Camera camera = Camera.getInstance();
		/*
		 * camera.setRotateX(Math.abs(diffY));
		 * camera.setRotateY(Math.abs(diffX));
		 * 
		 * double newAngle = diffX + diffY + camera.getRotatingAngle(); if
		 * (newAngle > 0) newAngle %= 360; else newAngle %= -360;
		 * 
		 * camera.setRotatingAngle(newAngle);
		 */

		// TODO
		// radius
		double x2 = Utils.sqr(camera.getEyeX());
		double y2 = Utils.sqr(camera.getEyeY());
		double z2 = Utils.sqr(camera.getEyeZ());
		double r2 = x2 + y2 + z2;
		double r = Math.sqrt(r2);

		// x rotating
		double dx = camera.getEyeX() + diffX * signX;
		if (r2 - y2 - (dx * dx) < 0) {
			dx = camera.getEyeX(); //r * (dx > 0 ? 1 : -1);
			signX = -signX;
			signZ = -signZ;
		}

		camera.setEyeX(dx);
		x2 = Utils.sqr(camera.getEyeX());
		double z = signZ * Math.sqrt(r2 - x2 - y2);

		camera.setEyeZ(z);

		if (log.isDebugEnabled()) {
			log.debug(String
					.format("z: %.2f, dx: %.2f, r: %.2f, r2: %.2f, x2: %.2f, y2: %.2f, r2 - x2 - y2: %.2f",
							z, dx, r, r2, x2, y2, r2 - x2 - y2));
		}

		// y rotating
		double dy = camera.getEyeY() + diffY * signY;
		if (r2 - x2 - (dy * dy) < 0) {
			dy = camera.getEyeY(); //r * (dy > 0 ? 1 : -1);
			;
			signY = -signY;
			signZ = -signZ;
		}

		camera.setEyeY(dy);
		y2 = Utils.sqr(camera.getEyeY());
		z = signZ * Math.sqrt(r2 - x2 - y2);
		camera.setEyeZ(z);

		if (log.isDebugEnabled()) {
			log.debug(String
					.format("z: %.2f, dy: %.2f, r: %.2f, r2: %.2f, x2: %.2f, y2: %.2f, r2 - x2 - y2: %.2f",
							z, dx, r, r2, x2, y2, r2 - x2 - y2));
		}

		lastX = x;
		lastY = y;

		if (log.isDebugEnabled()) {
			log.debug("diffX " + diffX + " diffY " + diffY);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();

		if (log.isDebugEnabled()) {
			log.debug("X " + lastX + " Y " + lastY);
		}
	}

	private int lastX;
	private int lastY;

	private int signX = 1;
	private int signY = 1;
	private int signZ = 1;

	private static final Logger log = Logger
			.getLogger(GLCanvasMouseListener.class);
}
