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
        Camera.getInstance().addEyeZ(v);

        if (log.isDebugEnabled()) {
            log.debug("Mouse wheeled: " + v);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int diffX = lastX - x;
        int diffY = lastY - y;

        Camera.getInstance().setRotateX(Math.abs(diffY));
        Camera.getInstance().setRotateY(Math.abs(diffX));
        
        double newAngle = diffX + diffY + Camera.getInstance().getRotatingAngle();
        if (newAngle > 0)
            newAngle %= 360;
        else
            newAngle %= -360;
        
        Camera.getInstance().setRotatingAngle(newAngle);
        Camera.getInstance().setEyeY(diffY);

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

    private static final Logger log = Logger.getLogger(GLCanvasMouseListener.class);
}
