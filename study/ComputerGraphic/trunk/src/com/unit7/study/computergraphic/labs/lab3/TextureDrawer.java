package com.unit7.study.computergraphic.labs.lab3;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import com.unit7.study.computergraphic.labs.lab2.DrawerStrategy;

public class TextureDrawer implements DrawerStrategy {
	@Override
	public void display(GL2 gl) {
		if (!isInitiated) {
			isInitiated = true;
			init(gl);
		}

		clear(gl);
		gl.glPushMatrix();

		for (int i = 0; i < textures.length; ++i) {
			gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[i]);
			gl.glBegin(GL2.GL_QUADS);

			gl.glTexCoord3f(0, 0, 0);
			gl.glVertex3f(0, 0, 0);
			gl.glTexCoord3f(1, 0, 0);
			gl.glVertex3f(1, 0, 0);
			gl.glTexCoord3f(1, 1, 0);
			gl.glVertex3f(1, 1, 0);
			gl.glTexCoord3f(0, 1, 0);
			gl.glVertex3f(0, 1, 0);

			gl.glEnd();

		}

		gl.glPopMatrix();
	}

	@Override
	public void clear(GL2 gl) {
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	}

	public void init(GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
		textures = new int[images.length];
		gl.glGenTextures(textures.length, textures, 0);
		for (int i = 0; i < images.length; ++i) {
			ByteBuffer dest = null;
			BufferedImage img = images[i];
			switch (img.getType()) {
			case BufferedImage.TYPE_3BYTE_BGR:
			case BufferedImage.TYPE_CUSTOM: {
				byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer())
						.getData();
				dest = ByteBuffer.allocateDirect(data.length);
				dest.order(ByteOrder.nativeOrder());
				dest.put(data, 0, data.length);
				break;
			}
			case BufferedImage.TYPE_INT_RGB: {
				int[] data = ((DataBufferInt) img.getRaster().getDataBuffer())
						.getData();
				dest = ByteBuffer.allocateDirect(data.length * 4);
				dest.order(ByteOrder.nativeOrder());
				dest.asIntBuffer().put(data, 0, data.length);
				break;
			}
			default:
				throw new RuntimeException("Unsupported image type "
						+ img.getType());
			}

			gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGB, img.getWidth(),
					img.getHeight(), 0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, dest);
		}

		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
				GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER,
				GL2.GL_LINEAR);
	}

	public void setImages(BufferedImage[] images) {
		this.images = images;
	}

	private boolean isInitiated;
	private int[] textures;
	private BufferedImage[] images;
}
