/**
 * Date:	15 янв. 2014 г.
 * File:	BackGround.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine.drawable;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.Texture;
import com.unit7.study.computergraphic.solarsystem.engine.Nameable;

/**
 * @author unit7
 *
 */
public class BackGround implements Drawable, Nameable {
    public BackGround() {
        this.name = "Background";
    }
    
    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable#draw(javax.media.opengl.GL2)
     */
    @Override
    public void draw(GL2 gl) {
        double r = 10;
        double z = 0.9;
        
        gl.glOrtho(0, 0, width, height, -1, 1);
        
        gl.glBegin(GL2.GL_QUADS);
        
        gl.glVertex3d(0 - width / 2 / r, 0 - height / 2 / r, z);
        gl.glTexCoord3d(0, 0, 0);
        gl.glVertex3d(0 - width / 2 / r, height / r, z);
        gl.glTexCoord3d(0, 1, 0);
        gl.glVertex3d(width / r, height / r, 0);
        gl.glTexCoord3d(1, 1, 0);
        gl.glVertex3d(width / r, 0 - height / 2 / r, z);
        gl.glTexCoord3d(1, 0, 0);
        
        gl.glEnd();
        
        new GLU().gluPerspective(45, width / height, 0, 1);
    }

    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable#getTexture()
     */
    @Override
    public Texture getTexture() {
        return texture;
    }

    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable#setTexture(com.jogamp.opengl.util.texture.Texture)
     */
    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable#isTextureEnabled()
     */
    @Override
    public boolean isTextureEnabled() {
        return textureEnabled;
    }

    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable#setTextureEnabled(boolean)
     */
    @Override
    public void setTextureEnabled(boolean textureEnabled) {
        this.textureEnabled = textureEnabled;

    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.Nameable#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    /* (non-Javadoc)
     * @see com.unit7.study.computergraphic.solarsystem.engine.Nameable#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    private Texture texture;
    private boolean textureEnabled;
    
    private double width;
    private double height;
    
    private String name;
}
