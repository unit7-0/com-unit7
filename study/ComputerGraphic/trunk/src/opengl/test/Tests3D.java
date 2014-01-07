/**
 * Date:	30 дек. 2013 г.:7:41:06
 * File:	Tests3D.java
 * 
 * Author:	Zajcev V.
 */

package opengl.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.DebugGL2;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class Tests3D {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas canvas = new GLCanvas(glCapabilities);
        Listener1 listener = new Listener1();
        listener.setCanvas(canvas);
        canvas.addGLEventListener(listener);
        
        JFrame frame = new JFrame("GL");
        frame.getContentPane().add(canvas);
//        frame.pack();
        frame.setSize(400, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

class Listener1 implements GLEventListener {
	@Override
    public void display(GLAutoDrawable drawable) {
		final GL2 gl = (GL2) drawable.getGL();

        // Clear screen.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Set camera.
        setCamera(gl, glu, 30);

        // Prepare light parameters.
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {-30, 0, 0, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);

        // Enable lighting in GL.
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);

        // Set material properties.
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL2.GL_SHININESS, 0.5f);

        // Apply texture.
        earthTexture.enable(gl);
        earthTexture.bind(gl);

        // Draw sphere (possible styles: FILL, LINE, POINT).
        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricTexture(earth, true);
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = 6.378f;
        final int slices = 16;
        final int stacks = 16;
        glu.gluSphere(earth, radius, slices, stacks);
        glu.gluDeleteQuadric(earth);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        
    }

    @Override
    public void init(GLAutoDrawable drawable) {
    	GL2 gl = (GL2) drawable.getGL();
    	drawable.setGL(new DebugGL2(gl));
    	
    	 gl.glEnable(GL2.GL_DEPTH_TEST);
         gl.glDepthFunc(GL2.GL_LEQUAL);
         gl.glShadeModel(GL2.GL_SMOOTH);
         gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
         gl.glClearColor(0f, 0f, 0f, 1f);

         try {
             InputStream stream = new FileInputStream("/home/unit7/earth.png");
             TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
             earthTexture = TextureIO.newTexture(data);
         }
         catch (IOException exc) {
             exc.printStackTrace();
             System.exit(1);
         }
         
         // Start animator (which should be a field).
         animator = new Animator();
         animator.start();
    }

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		GL2 gl = (GL2) arg0.getGL();
        gl.glViewport(0, 0, arg3, arg4);		
	}
	
	private void setCamera(GL2 gl, GLU glu, float distance) {
        // Change to projection matrix.
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) canvas.getWidth() / (float) canvas.getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(0, 0, distance, 0, 0, 0, 0, 0.005f, 0);

        // Change back to model view matrix.
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
	
	public void setCanvas(GLCanvas canvas) {
		this.canvas = canvas;
	}
	
	private GLU glu = new GLU();
	private Animator animator;
	private GLCanvas canvas;
	private Texture earthTexture;
}