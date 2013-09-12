package com.unit7.study.computergraphic.labs.lab2; 
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
 import javax.media.opengl.GLEventListener;
 import javax.media.opengl.GLAutoDrawable;
 import javax.media.opengl.glu.GLU;

import java.awt.Color;
 import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
 
 public class JavaRenderer 
 implements GLEventListener, KeyListener {
    private float rotateT = 0.0f;
    private static final GLU glu = new GLU();
 
    public JavaRenderer(int n) {
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
    
    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
 
        for (int i = 0; i < heightMatrix.length - 1; ++i) {
            for (int j = 0; j < heightMatrix[i].length - 1; ++j) {
                float x = i * ZOOM;
                float y = j * ZOOM;
                
                gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                    gl.glColor3ub((byte) colorMap[i][j].getRed(), (byte) colorMap[i][j].getGreen(), (byte) colorMap[i][j].getBlue());
                    gl.glVertex3f(x, y, heightMatrix[i][j]);
                    gl.glColor3ub((byte) colorMap[i + 1][j].getRed(), (byte) colorMap[i + 1][j].getGreen(), (byte) colorMap[i + 1][j].getBlue());
                    gl.glVertex3f(x + ZOOM, y, heightMatrix[i + 1][j]);
                    gl.glColor3ub((byte) colorMap[i][j + 1].getRed(), (byte) colorMap[i][j + 1].getGreen(), (byte) colorMap[i][j + 1].getBlue());
                    gl.glVertex3f(x, y + ZOOM, heightMatrix[i][j + 1]);
                    gl.glColor3ub((byte) colorMap[i + 1][j + 1].getRed(), (byte) colorMap[i + 1][j + 1].getGreen(), (byte) colorMap[i + 1][j + 1].getBlue());
                    gl.glVertex3f(x + ZOOM, y + ZOOM, heightMatrix[i + 1][j + 1]);
                gl.glEnd();
            }
        }
 
        rotateT += 0.2f;
    }
 
    public void displayChanged(GLAutoDrawable gLDrawable, 
      boolean modeChanged, boolean deviceChanged) {
    }
 
    public void init(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, 
        GL.GL_NICEST);
        //gLDrawable.addKeyListener(this);
    }
 
    public void reshape(GLAutoDrawable gLDrawable, int x, 
    int y, int width, int height) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        if(height <= 0) {
            height = 1;
        }
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
 
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            JavaDia.bQuit = true;
            JavaDia.displayT = null;
            System.exit(0);
        }
    }
 
    public void keyReleased(KeyEvent e) {
    }
 
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub
        
    }
    
    private int[][] heightMatrix;
    private Color[][] colorMap;
    public static final int MAX_HEIGHT = 10;
    public static final float ZOOM = 16;
 }