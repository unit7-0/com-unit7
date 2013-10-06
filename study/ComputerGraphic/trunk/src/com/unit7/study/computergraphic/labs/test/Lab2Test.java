package com.unit7.study.computergraphic.labs.test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import junit.framework.TestCase;

import com.unit7.study.computergraphic.labs.lab2.Drawer;
import com.unit7.study.computergraphic.labs.lab2.HilledLandScapeGenerator;
import com.unit7.study.computergraphic.labs.lab2.Initializer3DGL;
import com.unit7.study.computergraphic.labs.lab2.LandScapeDrawer;
import com.unit7.study.computergraphic.labs.lab2.MouseState;
import com.unit7.study.computergraphic.labs.lab2.MouseStateImpl;
import com.unit7.study.computergraphic.labs.lab2.Normalizer;

public class Lab2Test {
    @Test
    public void draw() {
        // user
        final LandScapeDrawer strategy = new LandScapeDrawer(512);
        strategy.setMapGenerator(new HilledLandScapeGenerator());
        strategy.regenerateLandScape();
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
                        strategy.setAngle(strategy.getAngle() + x - mouseState.getX());
                    } else {
                        strategy.setxAxis(1);
                        strategy.setyAxis(0);
                        strategy.setzAxis(0);
                        strategy.setAngle(strategy.getAngle() + y - mouseState.getY());
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
    
    @Test
    public void vallying() {
        HilledLandScapeGenerator generator = new HilledLandScapeGenerator();
        float[][] map = generator.generateMap(512);
        ArrayList<int[]> indexes = new ArrayList();
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                if (map[i][j] < 0) {
                    indexes.add(new int[] { i, j} );
                }
            }
        }
        
        for (int[] index : indexes) {
            writer.println(String.format("map[%d][%d] = %d", map[index[0]][index[1]]));
        }
    }
    
    @Test
    public void mapAverage() {
        HilledLandScapeGenerator generator = new HilledLandScapeGenerator();
        float[][] map = generator.generateMap(512);
        float amount = 0;
        float min = Float.MAX_VALUE;
        ArrayList<int[]> indexes = new ArrayList();
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                amount += map[i][j];
                min = Math.min(min, map[i][j]);
            }
        }
        
        writer.println("Average value: " + amount / (map.length * map.length) + " min: " + min);
    }
    
    @Test 
    public void mapToFile() {
        HilledLandScapeGenerator generator = new HilledLandScapeGenerator();
        float[][] map = generator.generateMap(512);
        float amount = 0;
        float min = Float.MAX_VALUE;
        ArrayList<int[]> indexes = new ArrayList();
        PrintWriter log = null;
        try {
            log = new PrintWriter(new FileOutputStream("/home/unit7/map.log"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                log.print(map[i][j] + "\t");
            }
            log.println();
        }
    }
    
    private boolean quite;
    private PrintWriter writer = new PrintWriter(System.out, true);
}
