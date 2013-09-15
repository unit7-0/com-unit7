package com.unit7.study.computergraphic.labs.test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import junit.framework.TestCase;

import com.unit7.study.computergraphic.labs.lab2.Drawer;
import com.unit7.study.computergraphic.labs.lab2.Initializer3DGL;
import com.unit7.study.computergraphic.labs.lab2.LandScapeDrawer;
import com.unit7.study.computergraphic.labs.lab2.MouseState;
import com.unit7.study.computergraphic.labs.lab2.MouseStateImpl;

public class Lab2Test extends TestCase {
    public void testDraw() {
        // user
        final LandScapeDrawer strategy = new LandScapeDrawer(512);
        Drawer drawer = new Drawer(strategy);
        
        // openGL
        GLEventListener initializer = new Initializer3DGL(drawer);
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        final GLCanvas canvas = new GLCanvas(glCapabilities);
        canvas.addGLEventListener(initializer);
        
        // swing
        JFrame frame = new JFrame("LandscapeDrawer");
        frame.getContentPane().add(canvas);
        frame.setSize(768, 768);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                quite = true;
            }
        });
        
        final MouseState mouseState = new MouseStateImpl();
        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mouseState.setState(false);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                mouseState.setState(true);
                mouseState.setXY(e.getX(), e.getY());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                strategy.regenerateLandScape();
            }
        });
        
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseState.getState()) {
                    int x = e.getX();
                    int y = e.getY();
                    if (Math.abs(mouseState.getX() - x) > Math.abs(mouseState.getY() - y)) {
                        strategy.setyAxis(1);
                        strategy.setxAxis(0);
                        strategy.setzAxis(0);
                        strategy.setAngle(x - mouseState.getX());
                    } else {
                        strategy.setxAxis(1);
                        strategy.setyAxis(0);
                        strategy.setzAxis(0);
                        strategy.setAngle(y - mouseState.getY());
                    }
                    
                    mouseState.setXY(x, y);
                }
                
                super.mouseMoved(e);
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
