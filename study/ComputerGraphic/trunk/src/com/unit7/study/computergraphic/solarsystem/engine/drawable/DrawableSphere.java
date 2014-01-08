/**
 * Date:	07 янв. 2014 г.
 * File:	DrawableSphere.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine.drawable;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import org.apache.log4j.Logger;

import com.jogamp.opengl.util.gl2.GLUT;
import com.unit7.study.computergraphic.solarsystem.engine.Camera;
import com.unit7.study.computergraphic.solarsystem.engine.SpaceObject;
import com.unit7.study.computergraphic.solarsystem.engine.Sphere;

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
        double addRatio = 1.5;
        double x = getObject().getX() * getRatio() * addRatio;
        double y = getObject().getY() * getRatio() * addRatio;
        double z = getObject().getZ() * getRatio() * addRatio;
        if (log.isDebugEnabled()) {
            log.debug(String.format("x: %.2f, y: %.2f, z: %.2f", x, y, z));
        }

        gl.glTranslated(x, y, z);
        // gl.glRotated(20, 1, 0, 0);
//        Camera.getInstance().setEyeZ(90000000);
        gl.glColor3f(1.0f, 0.0f, 0f);
        // TODO
        double radius = ((Sphere) getObject()).getRadius() * getRatio();
        if (log.isDebugEnabled()) {
            log.debug(String.format("radius: %.2f", radius));
        }

        glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
        glu.gluQuadricNormals(quadric, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(quadric, GLU.GLU_OUTSIDE);
        glu.gluSphere(quadric, radius, 100, 100);
        glu.gluDeleteQuadric(quadric);
        /*
        gl.glTranslated(0, 2782040, 0);
        quadric = glu.gluNewQuadric();
        glu.gluSphere(quadric, 978204, 100, 100);
        glu.gluDeleteQuadric(quadric);*/
    }

    @Override
    public void setObject(SpaceObject object) {
        if (!(object instanceof Sphere))
            throw new IllegalArgumentException("object not a sphere");

        super.setObject(object);
    }

    private static final Logger log = Logger.getLogger(DrawableSphere.class);
}
