package com.unit7.study.computergraphic.labs.lab2;

import java.awt.Color;
import java.nio.FloatBuffer;
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
               
        gl.glTranslatef(-.8f, -0.7f, -4.f);
        gl.glRotatef(angle, xAxis, yAxis, zAxis);        
        
        // draw
        
        for (int i = 0; i < hMatrix.length - 1; ++i) {
            for (int j = 0; j < hMatrix[0].length - 1; ++j) {
//                gl.glBegin(GL2.GL_QUADS);
                
                float h = hMatrix[i][j] * ZOOM;
                float x = i * ZOOM_XY;
                float y = j * ZOOM_XY;
        /*        gl.glColor3f(0, hMatrix[i][j], 0);
                gl.glVertex3f(x, y, h);
        */        
                points[i * hMatrix.length * 12 + j * 12] = x;
                points[i * hMatrix.length * 12 + j * 12 + 1] = y;
                points[i * hMatrix.length * 12 + j * 12 + 2] = h;
                
                colors[i * hMatrix.length * 12 + j * 12] = 0;
                colors[i * hMatrix.length * 12 + j * 12 + 1] = h;
                colors[i * hMatrix.length * 12 + j * 12 + 2] = 0;
                
                h = hMatrix[i][j + 1] * ZOOM;
                y = (j + 1) * ZOOM_XY;
         /*       gl.glColor3f(0, hMatrix[i][j + 1], 0);
                gl.glVertex3f(x, y + ZOOM_XY, h);
         */       
                
                points[i * hMatrix.length * 12 + j * 12 + 3] = x;
                points[i * hMatrix.length * 12 + j * 12 + 4] = y;
                points[i * hMatrix.length * 12 + j * 12 + 5] = h;
                
                colors[i * hMatrix.length * 12 + j * 12 + 3] = 0;
                colors[i * hMatrix.length * 12 + j * 12 + 4] = h;
                colors[i * hMatrix.length * 12 + j * 12 + 5] = 0;
                
                h = hMatrix[i + 1][j + 1] * ZOOM;
                x = (i + 1) * ZOOM_XY;
                
                points[i * hMatrix.length * 12 + j * 12 + 6] = x;
                points[i * hMatrix.length * 12 + j * 12 + 7] = y;
                points[i * hMatrix.length * 12 + j * 12 + 8] = h;
                
                colors[i * hMatrix.length * 12 + j * 12 + 6] = 0;
                colors[i * hMatrix.length * 12 + j * 12 + 7] = h;
                colors[i * hMatrix.length * 12 + j * 12 + 8] = 0;
                
         /*       gl.glColor3f(0, hMatrix[i + 1][j], 0);
                gl.glVertex3f(x + ZOOM_XY, y + ZOOM_XY, h);
         */       
                h = hMatrix[i + 1][j] * ZOOM;
                x = (i + 1) * ZOOM_XY;
                y = j * ZOOM_XY;
                
                points[i * hMatrix.length * 12 + j * 12 + 9] = x;
                points[i * hMatrix.length * 12 + j * 12 + 10] = y;
                points[i * hMatrix.length * 12 + j * 12 + 11] = h;
                
                colors[i * hMatrix.length * 12 + j * 12 + 9] = 0;
                colors[i * hMatrix.length * 12 + j * 12 + 10] = h;
                colors[i * hMatrix.length * 12 + j * 12 + 11] = 0;
                
         /*       gl.glColor3f(0, hMatrix[i + 1][j], 0);
                gl.glVertex3f(x + ZOOM_XY, y, h);
                
                gl.glEnd();
         */   }
            
        }
        
        
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
        
        FloatBuffer pointsBuff = Buffers.newDirectFloatBuffer(points);
        
        FloatBuffer colorsBuff = Buffers.newDirectFloatBuffer(colors);
        
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, pointsBuff);
        gl.glColorPointer(3, GL2.GL_FLOAT, 0, colorsBuff);
        
        gl.glDrawArrays(GL2.GL_QUADS, 0, points.length);
        
        gl.glFlush();
        
        
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
        
        gl.glPopMatrix();
    }
    
    @Override
    public void clear(GL2 gl) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }

    private void generateMap(int n) {
        hMatrix = mapGenerator.generateMap(n);
    }
    
    public void regenerateLandScape() {
        mapGenerator.regenerateMap(hMatrix);
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
    private float ZOOM_XY = 0.0031f;
    private MapGenerator mapGenerator;
    
    private float angle = 0;
    private float xAxis = 0;
    private float yAxis = 0;
    private float zAxis = 0;
    
    private float[] points;
    private float[] colors;
    
    private Random rand = new Random(System.currentTimeMillis());
}