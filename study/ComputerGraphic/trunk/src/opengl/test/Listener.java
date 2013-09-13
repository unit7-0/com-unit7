package opengl.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;

public class Listener implements GLEventListener {
    public Listener() {
        hMatrix = new float[2048][2048];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < hMatrix.length; ++i) {
            for (int j = 0; j < hMatrix[0].length; ++j) {
                hMatrix[i][j] = rand.nextFloat();
            }
        }
    }
    
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        //draw
        gl.glTranslatef(-5, 0, -5f);
        gl.glRotatef(-55, 1, 0, 0);
        for (int i = 0; i < hMatrix.length - 1; ++i) {
            for (int j = 0; j < hMatrix[0].length - 1; ++j) {
                gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                
                float h = hMatrix[i][j] * ZOOM;
                float x = i * ZOOM_XY;
                float y = j * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i][j], 0);
                gl.glVertex3f(x, y, h);
                
                h = hMatrix[i][j + 1] * ZOOM;
                y = (j + 1) * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i][j + 1], 0);
                gl.glVertex3f(x, y + ZOOM_XY, h);
                
                h = hMatrix[i + 1][j] * ZOOM;
                x = (i + 1) * ZOOM_XY;
                y = j * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i + 1][j], 0);
                gl.glVertex3f(x + ZOOM_XY, y, h);
                
                h = hMatrix[i + 1][j + 1] * ZOOM;
                y = (j + 1) * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i + 1][j], 0);
                gl.glVertex3f(x + ZOOM_XY, y + ZOOM_XY, h);
                
                gl.glEnd();
            }
        }
        
        /*
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(1, 0, 0);
        gl.glVertex3f(0, 0, 0);
        
        gl.glColor3f(0, 1, 0);
        gl.glVertex3f(100, 0, 0);
        
        gl.glColor3f(0, 0, 1);
        gl.glVertex3f(100, 100, 0);
        
        gl.glColor3f(0.5f, 0.5f, 0.5f);
        gl.glVertex3f(0, 100, 0);
        gl.glEnd();*/
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width,
            int height) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        if (height <= 0)
            height = 1;
        
        final float h = (float) width / height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, h, 1.0, 1024);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    private GLU glu = new GLU();
    private float[][] hMatrix;
    private float ZOOM = 1;
    private float ZOOM_XY = 0.0031f;
}
