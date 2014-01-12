/**
 * Date:	11 янв. 2014 г.
 * File:	MouseCoordGetter.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.media.opengl.awt.GLCanvas;

/**
 * @author unit7
 * 
 */
public class MouseCoordGetter extends MouseAdapter {
    public MouseCoordGetter(GLCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        double xd = (2 * x) / (double) (canvas.getWidth()) - 1;
        double yd = (2 * (canvas.getHeight() - y)) / (double) (canvas.getHeight()) - 1;
        
        x = (int) (xd * 1000);
        y = (int) (yd * 1000);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int x, y;
    private GLCanvas canvas;
}
