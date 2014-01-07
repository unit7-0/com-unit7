/**
 * Date:	04 янв. 2014 г.
 * File:	SpaceDrawableObject.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine.drawable;

import com.unit7.study.computergraphic.solarsystem.engine.SpaceObject;


/**
 * @author unit7
 * 
 */
public abstract class DrawableSpaceObject implements Drawable {
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

    private SpaceObject object;
    private double ratio;
}
