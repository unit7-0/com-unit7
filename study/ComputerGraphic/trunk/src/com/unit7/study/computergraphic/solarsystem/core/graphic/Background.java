/**
 * Date:	15 янв. 2014 г.
 * File:	BackGround.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core.graphic;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.unit7.study.computergraphic.solarsystem.core.graphic.drawable.Drawable;
import com.unit7.study.computergraphic.solarsystem.core.interfaces.Nameable;

/**
 * @author unit7
 * 
 */
public class Background implements Drawable, Nameable {
	public Background() {
		this.name = "Background";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable#
	 * draw(javax.media.opengl.GL2)
	 */
	@Override
	public void draw(GL2 gl) {
		double r = 10;
		double z = 0;

		CreateSkyBox(gl, 0, 0, 0, 600, 600, 400);
	}
	
	void CreateSkyBox(GL2 gl, float x, float y, float z, float width, float height, float length)
	{
	    // Это самая важная функция в этом уроке. Мы накладываем на каждую сторону текстуру,
	    // чтобы создать иллюзию 3д мира. Вы заметите, что я изменил текстурные
	    // координаты, чтобы стороны корректно выглядели. Также, в зависимости от
	    // ситуации, вершины могут быть перевернуты. В этом уроке такого не будет,
	    // но имейте в виду такую возможность.

	    // Так как мы хотим, чтобы скайбокс был с центром в x-y-z, мы производим
	    // небольшие рассчеты. Просто изменяем X,Y и Z на нужные.

	    // Это центрирует скайбокс на X,Y,Z
	    x = x - width  / 2;
	    y = y - height / 2;
	    z = z - length / 2;

	    gl.glBegin(GL2.GL_QUADS);
	        // Установим текстурные координаты и вершины ЗАДНЕЙ стороны
	        gl.glTexCoord2f(1.0f, 0.0f);
	        gl.glVertex3f(x + width, y,z);
	        gl.glTexCoord2f(1.0f, 1.0f); 
	        gl.glVertex3f(x + width, y + height, z);
	        gl.glTexCoord2f(0.0f, 1.0f); 
	        gl.glVertex3f(x, y + height, z);
	        gl.glTexCoord2f(0.0f, 0.0f);
	        gl.glVertex3f(x, y,z);

	    gl.glEnd();

	    // Начинаем рисовать сторону
	    gl.glBegin(GL2.GL_QUADS);

	        // Установим текстурные координаты и вершины ПЕРЕДНЕЙ стороны
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(x, y, z + length);
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(x, y + height, z + length);
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(x + width, y + height, z + length);
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(x + width, y,z + length);
	    gl.glEnd();

	    // Начинаем рисовать сторону
	    gl.glBegin(GL2.GL_QUADS);

	        // Установим текстурные координаты и вершины НИЖНЕЙ стороны
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(x, y,z);
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(x, y,z + length);
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(x + width, y,z + length);
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(x + width, y,z);
	    gl.glEnd();

	    // Начинаем рисовать сторону
	    gl.glBegin(GL2.GL_QUADS);

	        // Установим текстурные координаты и вершины ВЕРХНЕЙ стороны
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(x + width, y + height, z);
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(x + width, y + height, z + length);
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(x, y + height,z + length);
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(x, y + height,z);

	    gl.glEnd();

	    // Начинаем рисовать сторону
	    gl.glBegin(GL2.GL_QUADS);

	        // Установим текстурные координаты и вершины ЛЕВОЙ стороны
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(x, y + height,z);
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(x, y + height,z + length);
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(x, y,z + length);
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(x, y,z);

	    gl.glEnd();

	    // Начинаем рисовать сторону
	    gl.glBegin(GL2.GL_QUADS);

	        // Установим текстурные координаты и вершины ПРАВОЙ стороны
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(x + width, y,z);
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(x + width, y,z + length);
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(x + width, y + height,z + length);
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(x + width, y + height,z);
	    gl.glEnd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable#
	 * getTexture()
	 */
	@Override
	public Texture getTexture() {
		return texture;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable#
	 * setTexture(com.jogamp.opengl.util.texture.Texture)
	 */
	@Override
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable#
	 * isTextureEnabled()
	 */
	@Override
	public boolean isTextureEnabled() {
		return textureEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable#
	 * setTextureEnabled(boolean)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.Nameable#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.computergraphic.solarsystem.engine.Nameable#setName(java
	 * .lang.String)
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
