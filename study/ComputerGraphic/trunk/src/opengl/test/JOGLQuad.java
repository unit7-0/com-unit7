package opengl.test;
import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.Animator;
 
/**
 * Self-contained example (within a single class only to keep it simple) 
 * displaying a rotating quad
 */
public class JOGLQuad implements GLEventListener {
 
        private float rotateT = 0.0f;
 
        @Override
        public void display(GLAutoDrawable gLDrawable) {
                final GL2 gl = gLDrawable.getGL().getGL2();
                gl.glClear(GL.GL_COLOR_BUFFER_BIT);
                gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, 0.0f, -5.0f);
 
                // rotate about the three axes
        /*        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
                gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
                gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
 */
                // Draw A Quad
                gl.glBegin(GL2.GL_QUADS);       
                gl.glColor3f(0.0f, 1.0f, 1.0f);   // set the color of the quad
                gl.glVertex3f(-1.0f, 1.0f, 0.0f);   // Top Left
                gl.glVertex3f( 1.0f, 1.0f, 0.0f);   // Top Right
                gl.glVertex3f( 1.0f,-1.0f, 0.0f);   // Bottom Right
                gl.glVertex3f(-1.0f,-1.0f, 0.0f);   // Bottom Left
                // Done Drawing The Quad
                gl.glEnd();                                                     
 
                // increasing rotation for the next iteration                   
                rotateT += 0.2f; 
        }
 
        @Override
        public void init(GLAutoDrawable glDrawable) {
                GL2 gl = glDrawable.getGL().getGL2();
                gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
                gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                gl.glClearDepth(1.0f);
                gl.glEnable(GL.GL_DEPTH_TEST);
                gl.glDepthFunc(GL.GL_LEQUAL);
                gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        }
 
        @Override
        public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
                GL2 gl = gLDrawable.getGL().getGL2();
                final float aspect = (float) width / (float) height;
                gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
                gl.glLoadIdentity();
                final float fh = 0.5f;
                final float fw = fh * aspect;
                //gl.glFrustumf(-fw, fw, -fh, fh, 1.0f, 1000.0f);
                glu.gluPerspective(45, aspect, 1, 1000);
                gl.glMatrixMode(GL2.GL_MODELVIEW);
                gl.glLoadIdentity();
        }
 
        @Override
        public void dispose(GLAutoDrawable gLDrawable) {
        }
        
        private GLU glu = new GLU();
}