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
    public LandScapeDrawer(float[][] hMatrix) {
        this.hMatrix = hMatrix;
        this.mapGenerator = new RandomLandScapeGenerator();
    }
    
    public LandScapeDrawer(int n) {
        this.mapGenerator = new RandomLandScapeGenerator();
        generateMap(n);
    }
    
    @Override
    public void display(GL2 gl) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        //gl.glLoadIdentity();
        
        gl.glPushMatrix();
               
        gl.glTranslatef(-.8f, -0.7f, -4.f);
        gl.glRotatef(angle, xAxis, yAxis, zAxis);        
        
        // draw
        for (int i = 0; i < hMatrix.length - 1; ++i) {
            for (int j = 0; j < hMatrix[0].length - 1; ++j) {
                gl.glBegin(GL2.GL_QUADS);
                
                float h = hMatrix[i][j] * ZOOM;
                float x = i * ZOOM_XY;
                float y = j * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i][j], 0);
                gl.glVertex3f(x, y, h);
                
                h = hMatrix[i][j + 1] * ZOOM;
                y = (j + 1) * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i][j + 1], 0);
                gl.glVertex3f(x, y + ZOOM_XY, h);
                
                h = hMatrix[i + 1][j + 1] * ZOOM;
                x = (i + 1) * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i + 1][j], 0);
                gl.glVertex3f(x + ZOOM_XY, y + ZOOM_XY, h);
                
                h = hMatrix[i + 1][j] * ZOOM;
                x = (i + 1) * ZOOM_XY;
                y = j * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i + 1][j], 0);
                gl.glVertex3f(x + ZOOM_XY, y, h);
                
                gl.glEnd();
            }
            
        }
        
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
    
    private void doBuildMap(float[][] map, int x1, int y1, int x2, int y2) {
        int xc = (x1 + x2) / 2;
        int yc = (y1 + y2) / 2;
        
        if (xc == x1 && yc == y1)
            return;
        
        boolean type = rand.nextBoolean();
        float vertexAdd = type ? UP : DOWN;
        float len = (x2 - x1) * vertexAdd;
        map[xc][y1] = ((map[x1][y1] + map[x2][y1]) / 2 + len) % MOD;
        float val = map[xc][y1];
        
        type = rand.nextBoolean();
        vertexAdd = type ? UP : DOWN;
        len = (x2 - x1) * vertexAdd;
        map[xc][y2] = ((map[x1][y2] + map[x2][y2]) / 2 + len) % MOD;
        val = map[xc][y2];
        
        type = rand.nextBoolean();
        vertexAdd = type ? UP : DOWN;
        len = (y2 - y1) * vertexAdd;
        map[x1][yc] = ((map[x1][y1] + map[x1][y2]) / 2 + len) % MOD;
        val = map[x1][yc];
        
        type = rand.nextBoolean();
        vertexAdd = type ? UP : DOWN;
        len = (y2 - y1) * vertexAdd;
        map[x2][yc] = ((map[x2][y1] + map[x2][y2]) / 2 + len) % MOD;
        val = map[x2][yc];
        
        type = rand.nextBoolean();
        vertexAdd = type ? UP : DOWN;
        len = ((x2 - x1) + (y2 - y1)) * vertexAdd;
        map[xc][yc] = ((map[xc][y1] + map[xc][y2] + map[x1][yc] + map[x2][yc]) / 4 + len) % MOD;
        val = map[xc][yc];
        
        doBuildMap(map, x1, y1, xc, yc);
        doBuildMap(map, x1, yc, xc, y2);
        doBuildMap(map, xc, y1, x2, yc);
        doBuildMap(map, xc, yc, x2, y2);
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
    
    private Random rand = new Random(System.currentTimeMillis());
    
    public static final float UP = 0.0005f;
    public static final float DOWN = 0.0005f;
    public static final float MOD = 1.1f;
}
