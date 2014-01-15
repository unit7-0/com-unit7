/**
 * Date:	07 янв. 2014 г.
 * File:	DrawableSphere.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core.graphic.drawable;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import org.apache.log4j.Logger;

import com.jogamp.opengl.util.gl2.GLUT;
import com.unit7.study.computergraphic.solarsystem.core.Camera;
import com.unit7.study.computergraphic.solarsystem.core.DrawnSphere;
import com.unit7.study.computergraphic.solarsystem.core.SpaceObject;
import com.unit7.study.computergraphic.solarsystem.core.Sphere;

/**
 * @author unit7
 * 
 */
public class DrawableSphere extends DrawableSpaceObject {

    /**
     * @param object
     */
    public DrawableSphere(Sphere object) {
        super(object);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.unit7.study.computergraphic.solarsystem.engine.Drawable#draw(javax
     * .media.opengl.GL2)
     */
    @Override
    public void draw(GL2 gl) {
        GLU glu = GLU.createGLU();
        GLUquadric quadric = glu.gluNewQuadric();
        Camera camera = Camera.getInstance();
        double addRatio = 1;// / getRatio();
        double x = getObject().getX() * camera.getRatio() * addRatio;
        double y = getObject().getY() * camera.getRatio() * addRatio;
        double z = getObject().getZ() * camera.getRatio() * addRatio;
        if (log.isDebugEnabled()) {
            log.debug(String.format("x: %.2f, y: %.2f, z: %.2f", x, y, z));
        }

        if (showOrbit) {
            SpaceObject obj = getObject();
            if (obj instanceof DrawnSphere) {
                DrawnSphere sphere = (DrawnSphere) obj;
                if (log.isDebugEnabled()) {
                    log.debug("drawnRadius: " + sphere.getDrawnRadius() + " ratio: " + camera.getRatio());
                }

                drawCircle(gl, sphere.getTarget().getX(), sphere.getTarget().getY(),
                        sphere.getDrawnRadius() * camera.getRatio(), 80);
            }
        }

        // gl.glRotated(20, 1, 0, 0);
        // Camera.getInstance().setEyeZ(90000000);
        // gl.glColor3f(1.0f, 0.0f, 0f);
        // TODO
        SpaceObject object = getObject();
        double radius = ((Sphere) object).getRadius() * camera.getRatio();
        gl.glTranslated(x, y, z);
        /*
         * if (object instanceof DrawnSphere) { gl.glRotated(((DrawnSphere)
         * object).getAngle(), 0, 1, 0); }
         */

        if (log.isDebugEnabled()) {
            log.debug(String.format("radius: %.2f", radius));
        }
        
        glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
        glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
        glu.gluQuadricOrientation(quadric, GLU.GLU_OUTSIDE);
        glu.gluQuadricTexture(quadric, true);
        glu.gluSphere(quadric, radius, 100, 100);
        glu.gluDeleteQuadric(quadric);
        
        if (object.isShowInfo()) {
            double pos = 0;
            double d = 3;
            gl.glColor3d(0, 0, 0.8);
            gl.glRasterPos3d(radius, radius - pos, 0);
            GLUT glut = new GLUT();
            
            /* for( int i = rec.first; i < rec.first + rec.num_chars; i++ )
       {
           bmpChar = rec.ch[i-rec.first];
           if( bmpChar == null )
           {
               continue;
           }
           gl.glNewList ( i, GL.GL_COMPILE );
           {
               gl.glBitmap ( bmpChar.width, bmpChar.height, 
                           bmpChar.xorig, bmpChar.yorig,
                           bmpChar.advance, 0,bmpChar.bitmap);
           }
           gl.glEndList ();
       }       

.../...

   public void drawString( GL gl, String s )
   {
       gl.glPushAttrib ( GL.GL_LIST_BIT );
       gl.glCallLists ( s.length (), GL.GL_UNSIGNED_SHORT, s.toCharArray () );
       gl.glPopAttrib ();
   }
*/
            
           /* glut.glutBitmapString(GLUT.BITMAP_9_BY_15, String.format("Status: %s", object.getStatus().getValue()));
            pos += d;
            gl.glRasterPos3d(radius, radius - pos, 0);
            glut.glutBitmapString(GLUT.BITMAP_9_BY_15, String.format("Name: %s", object.getName()));
            gl.glRasterPos3d(radius, radius - pos, 0);
            pos += d;
            glut.glutBitmapString(GLUT.BITMAP_9_BY_15, String.format("Name: %s", object.getName()));
            gl.glRasterPos3d(radius, radius - pos, 0);
            pos += d;
            glut.glutBitmapString(GLUT.BITMAP_9_BY_15, String.format("Age: %.2f bln. years", object.getAge()));
*/
            gl.glColor3d(1, 1, 1);
        }
    }

    protected void drawCircle(GL2 gl, double cx, double cy, double r, int num_segments) {
        gl.glBegin(GL2.GL_LINE_LOOP);

        cx *= Camera.getInstance().getRatio();
        cy *= Camera.getInstance().getRatio();
        for (int ii = 0; ii < num_segments; ii++) {
            double theta = 2.0 * Math.PI * (double) ii / (double) num_segments; // get
                                                                                // the
                                                                                // current
                                                                                // angle

            double x = r * Math.cos(theta); // calculate the x component
            double y = r * Math.sin(theta); // calculate the y component

            if (log.isDebugEnabled()) {
                log.debug(String.format("x + cx: %.2f, y + cy: %.2f, r: %.2f", x + cx, y + cy, r));
            }

            gl.glVertex3d(x + cx, y + cy, 0); // output vertex
        }

        gl.glEnd();
    }

    @Override
    public void setObject(SpaceObject object) {
        if (!(object instanceof Sphere))
            throw new IllegalArgumentException("object not a sphere");

        super.setObject(object);
    }

    public boolean isShowOrbit() {
        return showOrbit;
    }

    public void setShowOrbit(boolean showOrbit) {
        this.showOrbit = showOrbit;
    }

    private boolean showOrbit;

    private static final Logger log = Logger.getLogger(DrawableSphere.class);
}
