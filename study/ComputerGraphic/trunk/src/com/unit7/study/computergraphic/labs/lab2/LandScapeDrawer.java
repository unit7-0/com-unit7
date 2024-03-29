package com.unit7.study.computergraphic.labs.lab2;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Random;

import javax.media.opengl.GL2;

import com.jogamp.common.nio.Buffers;

/**
 * Рисовальщик ландшафта
 * @author unit7
 *
 */
public class LandScapeDrawer implements DrawerStrategy {
    public LandScapeDrawer(float[][] hMatrix) {
        this.hMatrix = hMatrix;
        this.mapGenerator = new RandomLandScapeGenerator();
        init();
    }
    
    public LandScapeDrawer(int n) {
        this.mapGenerator = new RandomLandScapeGenerator();
        generateMap(n);
        init();
    }
    
    public void init() {
        points = new float[3 * 4 * hMatrix.length * hMatrix.length];
        colors = new float[points.length];        
    }
    
    @Override
    public void display(GL2 gl) {
        clear(gl);
        
        gl.glPushMatrix();
               
        gl.glTranslatef(-300.1f, -0.f, -1100.5f);
        gl.glRotatef(angle, xAxis, yAxis, zAxis);  
        gl.glRotatef(-50, 1, 0, 0);
        gl.glRotatef(-50, 0, 0, 1);
        
        // draw
        for (int i = 0; i < hMatrix.length - 1; ++i) {
            for (int j = 0; j < hMatrix[0].length - 1; ++j) {
                gl.glBegin(GL2.GL_QUADS);
                
                float h = hMatrix[i][j] * ZOOM;
                float x = i * ZOOM_XY;
                float y = j * ZOOM_XY;
                float magic = 200;
                
                points[12 * (i * (hMatrix.length - 1) + j)] = x;
                points[12 * (i * (hMatrix.length - 1) + j) + 1] = y;
                points[12 * (i * (hMatrix.length - 1) + j) + 2] = h;
                
                colors[12 * (i * (hMatrix.length - 1) + j)] = 0;
                colors[12 * (i * (hMatrix.length - 1) + j) + 1] = (h - 0.000001f > 0 ? h / magic : 0.1f);
                colors[12 * (i * (hMatrix.length - 1) + j) + 2] = 0;
                
                gl.glColor3f(colors[12 * (i * (hMatrix.length - 1) + j)], colors[12 * (i * (hMatrix.length - 1) + j) + 1], colors[12 * (i * (hMatrix.length - 1) + j) + 2]);
                gl.glVertex3f(points[12 * (i * (hMatrix.length - 1) + j)], points[12 * (i * (hMatrix.length - 1) + j) + 1], points[12 * (i * (hMatrix.length - 1) + j) + 2]);
                
                h = hMatrix[i][j + 1] * ZOOM;
                y = (j + 1) * ZOOM_XY;
                
                points[12 * (i * (hMatrix.length - 1) + j) + 3] = x;
                points[12 * (i * (hMatrix.length - 1) + j) + 4] = y + ZOOM_XY;
                points[12 * (i * (hMatrix.length - 1) + j) + 5] = h;
                
                colors[12 * (i * (hMatrix.length - 1) + j) + 3] = 0;
                colors[12 * (i * (hMatrix.length - 1) + j) + 4] = (h - 0.000001f > 0 ? h / magic : 0.1f);
                colors[12 * (i * (hMatrix.length - 1) + j) + 5] = 0;
                
                gl.glColor3f(colors[12 * (i * (hMatrix.length - 1) + j) + 3], colors[12 * (i * (hMatrix.length - 1) + j) + 4], colors[12 * (i * (hMatrix.length - 1) + j) + 5]);
                gl.glVertex3f(points[12 * (i * (hMatrix.length - 1) + j) + 3], points[12 * (i * (hMatrix.length - 1) + j) + 4], points[12 * (i * (hMatrix.length - 1) + j) + 5]);
                
                h = hMatrix[i + 1][j + 1] * ZOOM;
                x = (i + 1) * ZOOM_XY;
                
                points[12 * (i * (hMatrix.length - 1) + j) + 6] = x + ZOOM_XY;
                points[12 * (i * (hMatrix.length - 1) + j) + 7] = y + ZOOM_XY;
                points[12 * (i * (hMatrix.length - 1) + j) + 8] = h;
                
                colors[12 * (i * (hMatrix.length - 1) + j) + 6] = 0;
                colors[12 * (i * (hMatrix.length - 1) + j) + 7] = (h - 0.000001f > 0 ? h / magic : 0.1f);
                colors[12 * (i * (hMatrix.length - 1) + j) + 8] = 0;
                
                gl.glColor3f(colors[12 * (i * (hMatrix.length - 1) + j) + 6], colors[12 * (i * (hMatrix.length - 1) + j) + 7], colors[12 * (i * (hMatrix.length - 1) + j) + 8]);
                gl.glVertex3f(points[12 * (i * (hMatrix.length - 1) + j) + 6], points[12 * (i * (hMatrix.length - 1) + j) + 7], points[12 * (i * (hMatrix.length - 1) + j) + 8]);
                
                h = hMatrix[i + 1][j] * ZOOM;
                x = (i + 1) * ZOOM_XY;
                y = j * ZOOM_XY;
                
                points[12 * (i * (hMatrix.length - 1) + j) + 9] = x + ZOOM_XY;
                points[12 * (i * (hMatrix.length - 1) + j) + 10] = y;
                points[12 * (i * (hMatrix.length - 1) + j) + 11] = h;
                
                colors[12 * (i * (hMatrix.length - 1) + j) + 9] = 0;
                colors[12 * (i * (hMatrix.length - 1) + j) + 10] = (h - 0.000001f > 0 ? h / magic : 0.1f);
                colors[12 * (i * (hMatrix.length - 1) + j) + 11] = 0;
                
                gl.glColor3f(colors[12 * (i * (hMatrix.length - 1) + j) + 9], colors[12 * (i * (hMatrix.length - 1) + j) + 10], colors[12 * (i * (hMatrix.length - 1) + j) + 11]);
                gl.glVertex3f(points[12 * (i * (hMatrix.length - 1) + j) + 9], points[12 * (i * (hMatrix.length - 1) + j) + 10], points[12 * (i * (hMatrix.length - 1) + j) + 11]);
                gl.glEnd();
            }
            
        }
        
        /*gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
        
        FloatBuffer pointsBuff = Buffers.newDirectFloatBuffer(points);
        FloatBuffer colorsBuff = Buffers.newDirectFloatBuffer(colors);
        
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, pointsBuff);
        gl.glColorPointer(3, GL2.GL_FLOAT, 0, colorsBuff);
        
        gl.glDrawArrays(GL2.GL_QUADS, 0, points.length);
        
        gl.glFlush();
        
        
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
        */
        gl.glPopMatrix();
    }
    
    @Override
    public void clear(GL2 gl) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }

    private void generateMap(int n) {
//        hMatrix = new float[n][n];
        hMatrix = mapGenerator.generateMap(n);
    }
    
    public void regenerateLandScape() {
        float[][] copy = new float[hMatrix.length][];
        for (int i = 0; i < copy.length; ++i) {
            copy[i] = Arrays.copyOf(hMatrix[i], hMatrix[i].length);
        }
        
        mapGenerator.regenerateMap(copy);
        hMatrix = copy;
    }
    
    public MapGenerator getMapGenerator() {
        return mapGenerator;
    }

    public void setMapGenerator(MapGenerator mapGenerator) {
        this.mapGenerator = mapGenerator;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getxAxis() {
        return xAxis;
    }

    public void setxAxis(float xAxis) {
        this.xAxis = xAxis;
    }

    public float getyAxis() {
        return yAxis;
    }

    public void setyAxis(float yAxis) {
        this.yAxis = yAxis;
    }

    public float getzAxis() {
        return zAxis;
    }

    public void setzAxis(float zAxis) {
        this.zAxis = zAxis;
    }

    private float[][] hMatrix;
    private float ZOOM = 1;
    private float ZOOM_XY = 1; //0.0031f;
    private MapGenerator mapGenerator;
    
    private float angle = 0;
    private float xAxis = 0;
    private float yAxis = 0;
    private float zAxis = 0;
    
    private float[] points;
    private float[] colors;
    
    private Random rand = new Random(System.currentTimeMillis());
}
