package com.unit7.study.computergraphic.labs.test;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.unit7.study.computergraphic.labs.lab2.Drawer;
import com.unit7.study.computergraphic.labs.lab2.DrawerStrategy;
import com.unit7.study.computergraphic.labs.lab2.Initializer3DGL;
import com.unit7.study.computergraphic.labs.lab2.LandScapeDrawer;

import junit.framework.TestCase;

public class Lab2Test extends TestCase {
    public void testDraw() {
        // user
        DrawerStrategy strategy = new LandScapeDrawer(128);
        Drawer drawer = new Drawer(strategy);
        
        // openGL
        GLEventListener initializer = new Initializer3DGL(drawer);
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas canvas = new GLCanvas(glCapabilities);
        canvas.addGLEventListener(initializer);
        
        // swing
        JFrame frame = new JFrame("LandscapeDrawer");
        frame.getContentPane().add(canvas);
        frame.setSize(128, 128);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                quite = true;
            }
        });
        frame.setVisible(true);
        canvas.requestFocus();
        frame.pack();
        
        while (!quite) {
            canvas.display();
        }
    }
    
    private boolean quite;
}
