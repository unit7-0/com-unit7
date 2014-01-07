package com.unit7.study.computergraphic.labs.test;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.unit7.study.computergraphic.labs.lab2.Drawer;
import com.unit7.study.computergraphic.labs.lab2.DrawerStrategy;
import com.unit7.study.computergraphic.labs.lab2.Initializer3DGL;
import com.unit7.study.computergraphic.labs.lab3.TextureDrawer;

import javax.imageio.ImageIO;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

public class Lab3Tests {
	@Test
	public void drawTexture() {
		GLCanvas canvas = initCanvas();
		// listeners
		
		JFrame frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				quiteDraw = true;
				super.windowClosing(e);
			}
		});
		frame.setSize(768, 768);
		frame.add(canvas);
		
		frame.setVisible(true);
		canvas.requestFocus();
		while (!quiteDraw) {
			canvas.display();
		}
	}
	
	@Test
	public void labTest() {
		
	}
	
	private GLCanvas initCanvas() {		
		DrawerStrategy strategy = initStrategy();
		Drawer drawer = new Drawer(strategy);
		Initializer3DGL initializer = new Initializer3DGL(drawer);
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities capabilities = new GLCapabilities(profile);
		GLCanvas canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(initializer);
		
		return canvas;
	}
	
	private DrawerStrategy initStrategy() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("texture"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TextureDrawer drawer = new TextureDrawer();
		drawer.setImages(new BufferedImage[] { image });
		return drawer;
	}
	
	private boolean quiteLab;
	private boolean quiteDraw;
}
