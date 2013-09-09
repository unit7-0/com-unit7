package com.unit7.study.computergraphic.labs.lab2;

import java.awt.Color;
import java.util.Random;

import javax.media.opengl.GL2;

/**
 * Рисовальщик ландшафта
 * @author unit7
 *
 */
public class LandScapeDrawer implements DrawerStrategy {
    public LandScapeDrawer(int[][] heightMatrix) {
        this.heightMatrix = heightMatrix;
        this.colorMap = new Color[heightMatrix.length][heightMatrix[0].length];
    }
    
    public LandScapeDrawer(int n) {
        heightMatrix = new int[n][n];
        colorMap = new Color[n][n];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                heightMatrix[i][j] = rand.nextInt(MAX_HEIGHT);
                colorMap[i][j] = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            }
        }
    }
    
    @Override
    public void display(GL2 gl) {
        for (int i = 0; i < heightMatrix.length - 1; ++i) {
            for (int j = 0; j < heightMatrix[i].length - 1; ++j) {
                float x = i * ZOOM;
                float y = j * ZOOM;
                
                gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                    gl.glColor3ub((byte) colorMap[i][j].getRed(), (byte) colorMap[i][j].getGreen(), (byte) colorMap[i][j].getBlue());
                    gl.glVertex3f(x, y, heightMatrix[i][j]);
                    gl.glColor3ub((byte) colorMap[i + 1][j].getRed(), (byte) colorMap[i + 1][j].getGreen(), (byte) colorMap[i + 1][j].getBlue());
                    gl.glVertex3f(x + ZOOM, y, heightMatrix[i][j]);
                    gl.glColor3ub((byte) colorMap[i][j + 1].getRed(), (byte) colorMap[i][j + 1].getGreen(), (byte) colorMap[i][j + 1].getBlue());
                    gl.glVertex3f(x, y + ZOOM, heightMatrix[i][j]);
                    gl.glColor3ub((byte) colorMap[i + 1][j + 1].getRed(), (byte) colorMap[i + 1][j + 1].getGreen(), (byte) colorMap[i + 1][j + 1].getBlue());
                    gl.glVertex3f(x + ZOOM, y + ZOOM, heightMatrix[i][j]);
                gl.glEnd();
            }
        }
    }

    @Override
    public void clear(GL2 gl) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }

    private int[][] heightMatrix;
    private Color[][] colorMap;
    public static final int MAX_HEIGHT = 10;
    public static final float ZOOM = 16;
}
