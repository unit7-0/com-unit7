/**
 * Date:	04 янв. 2014 г.
 * File:	Scene.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine;

import java.util.Collection;
import java.util.Iterator;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.apache.log4j.Logger;

import com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable;

/**
 * @author unit7
 *
 */
public class Renderer extends ObjectHolderImpl<Drawable> implements GLEventListener {

    /* (non-Javadoc)
     * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
     */
    @Override
    public void display(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub
        GL2 gl = (GL2) arg0.getGL();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        Collection<Drawable> objs = getObjects();
        if (log.isDebugEnabled()) {
            log.debug(String.format("objects to draw: [ %d ]", objs.size()));
        }
        
        for (Iterator<Drawable> it = objs.iterator(); it.hasNext();) {
            Drawable object = it.next();
            if (log.isDebugEnabled()) {
                log.debug(String.format("Object [ %s ] starting drawing", object));
            }
            
            gl.glPushMatrix();
            object.draw(gl);
            gl.glPopMatrix();
            if (log.isDebugEnabled()) {
                log.debug(String.format("Object [ %s ] finished drawing", object));
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.media.opengl.GLEventListener#dispose(javax.media.opengl.GLAutoDrawable)
     */
    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
     */
    @Override
    public void init(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub
        GL2 gl = arg0.getGL().getGL2();      // get the OpenGL graphics context
        glu = new GLU();                         // get GL Utilities
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
        gl.glClearDepth(1.0f);      // set clear depth value to farthest
        gl.glEnable(GL2.GL_DEPTH_TEST); // enables depth testing
        gl.glDepthFunc(GL2.GL_LEQUAL);  // the type of depth test to do
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // best perspective correction
        gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
//        glu.gluLookAt(0, 0, 50, 0, 0, 0, 0, 1, 0);
    }

    /* (non-Javadoc)
     * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable, int, int, int, int)
     */
    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int width, int height) {
        GL2 gl = arg0.getGL().getGL2();  // get the OpenGL 2 graphics context
        
        if (height == 0) height = 1;   // prevent divide by zero
        float aspect = (float)width / height;
   
        // Set the view port (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);
   
        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL2.GL_PROJECTION);  // choose projection matrix
        gl.glLoadIdentity();             // reset projection matrix
        glu.gluPerspective(45.0, aspect, 0, 1); // fovy, aspect, zNear, zFar
   
        // Enable the model-view transform
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // reset
    }
    
    private GLU glu;
    
    private static final Logger log = Logger.getLogger(Renderer.class);
}
