/**
 * Date:	04 янв. 2014 г.
 * File:	SpaceDrawableObject.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine.drawable;

import com.jogamp.opengl.util.texture.Texture;
import com.unit7.study.computergraphic.solarsystem.engine.Nameable;
import com.unit7.study.computergraphic.solarsystem.engine.SpaceObject;


/**
 * @author unit7
 * 
 */
public abstract class DrawableSpaceObject implements Drawable, Nameable {
    public DrawableSpaceObject(SpaceObject object) {
        this.object = object;
    }

    public SpaceObject getObject() {
        return object;
    }

    public void setObject(SpaceObject object) {
        this.object = object;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public String toString() {
        return String.format("DrawableSpaceObject [object=%s]", object);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public boolean isTextureEnabled() {
        return textureEnabled;
    }

    @Override
    public void setTextureEnabled(boolean textureEnabled) {
        this.textureEnabled = textureEnabled;
    }

    @Override
    public String getName() {
        return object.getName();
    }

    @Override
    public void setName(String name) {
        object.setName(name);
    }

    private SpaceObject object;
    private Texture texture;
    private boolean textureEnabled;
    private double ratio;
}
