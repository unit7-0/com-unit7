/**
 * Date:	04 янв. 2014 г.
 * File:	Drawable.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine.drawable;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

/**
 * @author unit7
 * 
 */
public interface Drawable {
    void draw(GL2 gl);

    Texture getTexture();

    void setTexture(Texture texture);

    boolean isTextureEnabled();

    void setTextureEnabled(boolean textureEnabled);
}
