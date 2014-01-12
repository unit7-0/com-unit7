/**
 * Date:	04 янв. 2014 г.
 * File:	App.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.app;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.GLProfile;
import javax.swing.JPanel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import com.unit7.study.computergraphic.solarsystem.engine.DrawnSphere;
import com.unit7.study.computergraphic.solarsystem.engine.DrawnSphere.Builder;
import com.unit7.study.computergraphic.solarsystem.engine.Camera;
import com.unit7.study.computergraphic.solarsystem.engine.GLCanvasKeyboardListener;
import com.unit7.study.computergraphic.solarsystem.engine.GLCanvasMouseListener;
import com.unit7.study.computergraphic.solarsystem.engine.GLFrame;
import com.unit7.study.computergraphic.solarsystem.engine.GravitationSystem;
import com.unit7.study.computergraphic.solarsystem.engine.MouseCoordGetter;
import com.unit7.study.computergraphic.solarsystem.engine.Renderer;
import com.unit7.study.computergraphic.solarsystem.engine.Scene;
import com.unit7.study.computergraphic.solarsystem.engine.Sphere;
import com.unit7.study.computergraphic.solarsystem.engine.Time;
import com.unit7.study.computergraphic.solarsystem.engine.Utils;
import com.unit7.study.computergraphic.solarsystem.engine.drawable.Drawable;
import com.unit7.study.computergraphic.solarsystem.engine.drawable.DrawableSpaceObject;
import com.unit7.study.computergraphic.solarsystem.engine.drawable.DrawableSphere;

/**
 * @author unit7
 * 
 */
public class App {
    public static void main(String[] args) {
        Renderer renderer = new Renderer();
        Scene scene = new Scene();
        GravitationSystem grSystem = new GravitationSystem();
        Time.getInstance().changeSpeed(100000);

        /**
         * сделаем все координаты в рамках 1000x1000
         */
        double coordMul = 1000;
        double plutoDrawnRadius = 5900000;
        double meruryDrawnRadius = 57910 / plutoDrawnRadius * coordMul;
        double venusDrawRadius = 108000 / plutoDrawnRadius * coordMul;
        double earthDrawnRadius = 149098.29 / plutoDrawnRadius * coordMul;
        double marsDrawnRadius = 229232 / plutoDrawnRadius * coordMul;
        double jupiterDrawnRadius = 770573.6 / plutoDrawnRadius * coordMul;
        double saturnDrawnRadius = 1613325.783 / plutoDrawnRadius * coordMul;
        double uranDrawnRadius = 2848938.461 / plutoDrawnRadius * coordMul;
        double neptunDrawnRadius = 4503946.49 / plutoDrawnRadius * coordMul;

        plutoDrawnRadius = coordMul;

        // star, Sun
        // уменьшим радиус
        Sphere sun = new Sphere.Builder().setName("Sun").setAge(4.57).setRadius(69551).setWeight(19891000).build();

        // planets
        DrawnSphere.Builder builder = new DrawnSphere.Builder();

        // pluto
        DrawnSphere pluto = (DrawnSphere) builder.setDrawnRadius(plutoDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(365 * 248)).setName("Pluto").setRadius(1153).setWeight(0.1305)
                .build();
        pluto.setX(sun.getX() - coordMul);

        // mercury
        DrawnSphere mercury = (DrawnSphere) builder.setDrawnRadius(meruryDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(87.97)).setAge(4.6).setName("Mercury").setRadius(2439.7)
                .setWeight(3.33022).build();
        mercury.setX(sun.getX() - mercury.getDrawnRadius());

        // venus
        DrawnSphere venus = (DrawnSphere) builder.setDrawnRadius(venusDrawRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(224.7)).setName("Venus").setRadius(6051.8).setWeight(48.67)
                .build();
        venus.setX(sun.getX() - venus.getDrawnRadius());

        // earth
        DrawnSphere earth = (DrawnSphere) builder.setDrawnRadius(earthDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(365.26)).setName("Earth").setRadius(6378.1).setWeight(59.72)
                .build();
        earth.setX(sun.getX() - earth.getDrawnRadius());

        // mars
        DrawnSphere mars = (DrawnSphere) builder.setDrawnRadius(marsDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(687)).setName("Mars").setRadius(3396.9).setWeight(6.39).build();
        mars.setX(sun.getX() - mars.getDrawnRadius());

        // Jupiter
        DrawnSphere jupiter = (DrawnSphere) builder.setDrawnRadius(jupiterDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(4332.6)).setName("Jupiter").setRadius(71492).setWeight(18980)
                .build();
        jupiter.setX(sun.getX() - jupiter.getDrawnRadius());

        // saturn
        DrawnSphere saturn = (DrawnSphere) builder.setDrawnRadius(saturnDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(10759.2)).setName("Saturn").setRadius(60268).setWeight(5683)
                .build();
        saturn.setX(sun.getX() - saturn.getDrawnRadius());

        // uran
        DrawnSphere uran = (DrawnSphere) builder.setDrawnRadius(uranDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(30685.4)).setName("Uranus").setRadius(25559).setWeight(868.1)
                .build();
        uran.setX(sun.getX() - uran.getDrawnRadius());

        // neptun
        DrawnSphere neptun = (DrawnSphere) builder.setDrawnRadius(neptunDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(60189)).setName("Neptune").setRadius(24764).setWeight(1024)
                .build();
        neptun.setX(sun.getX() - neptun.getDrawnRadius());

        DrawableSphere sunDraw = new DrawableSphere(sun);
        DrawableSphere mercuryDraw = new DrawableSphere(mercury);
        DrawableSphere venusDraw = new DrawableSphere(venus);
        DrawableSphere earthDraw = new DrawableSphere(earth);
        DrawableSphere marsDraw = new DrawableSphere(mars);
        DrawableSphere jupiterDraw = new DrawableSphere(jupiter);
        DrawableSphere saturnDraw = new DrawableSphere(saturn);
        DrawableSphere uranDraw = new DrawableSphere(uran);
        DrawableSphere neptunDraw = new DrawableSphere(neptun);
        DrawableSphere plutoDraw = new DrawableSphere(pluto);

        double ratio = 0.0001;
        Camera.getInstance().setRatio(ratio);
        sunDraw.setRatio(ratio);
        mercuryDraw.setRatio(ratio);
        venusDraw.setRatio(ratio);
        earthDraw.setRatio(ratio);
        marsDraw.setRatio(ratio);
        jupiterDraw.setRatio(ratio);
        saturnDraw.setRatio(ratio);
        uranDraw.setRatio(ratio);
        neptunDraw.setRatio(ratio);
        plutoDraw.setRatio(ratio);

        App app = new App();
        // loadTextures
        List<DrawableSpaceObject> objs = new ArrayList<DrawableSpaceObject>();
        objs.add(sunDraw);
        objs.add(mercuryDraw);
        objs.add(venusDraw);
        objs.add(earthDraw);
        objs.add(marsDraw);
        objs.add(jupiterDraw);
        objs.add(saturnDraw);
        objs.add(uranDraw);
        objs.add(neptunDraw);
        objs.add(plutoDraw);

        // add to drawing
        renderer.addObject(sunDraw).addObject(mercuryDraw).addObject(venusDraw).addObject(earthDraw)
                .addObject(marsDraw).addObject(jupiterDraw).addObject(saturnDraw).addObject(uranDraw)
                .addObject(neptunDraw).addObject(plutoDraw);

        // add to scene
        scene.addObject(sun).addObject(mercury).addObject(venus).addObject(earth).addObject(mars).addObject(jupiter)
                .addObject(saturn).addObject(uran).addObject(neptun).addObject(pluto);

        /*
         * mercury.setVy(-0.000000711); venus.setVy(-0.000000600);
         * earth.setVy(-0.000000500);
         * grSystem.addObject(sun).addObject(mercury).
         * addObject(venus).addObject(earth);.addObject(mars).addObject(jupiter)
         * .
         * addObject(saturn).addObject(uran).addObject(neptun).addObject(pluto);
         */

        GLFrame frame = new GLFrame(renderer);
        MouseAdapter mouseAdapter = new GLCanvasMouseListener();
        java.awt.event.KeyAdapter keyAdapter = new GLCanvasKeyboardListener();
        MouseCoordGetter coordGetter = new MouseCoordGetter(frame.getCanvas());

        class CoordPanel extends JPanel {
            /**
             * 
             */
            public CoordPanel(MouseCoordGetter getter) {
                this.getter = getter;
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = getter.getX();
                int y = getter.getY();
                g.setColor(Color.BLACK);
                g.drawString("x: " + x + " y: " + y, 50, 50);
            }
            
            private MouseCoordGetter getter;
        }
        
        frame.getCanvas().addMouseWheelListener(mouseAdapter);
        frame.getCanvas().addMouseListener(mouseAdapter);
        frame.getCanvas().addMouseMotionListener(mouseAdapter);
        frame.getCanvas().addKeyListener(keyAdapter);
        frame.getCanvas().addMouseMotionListener(coordGetter);
        frame.getContentPane().setLayout(new GridLayout(1, 2, 10, 10));
        final CoordPanel coordPanel = new CoordPanel(coordGetter);
//        frame.getContentPane().add(coordPanel);
        frame.setVisible(true);
        
        Timer timer = new Timer();
  /*      timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                coordPanel.paintComponent(coordPanel.getGraphics());
            }
        }, 0, 100);
  */      
        
        // new Thread(grSystem).start();
        Logger.getRootLogger().setLevel(Level.ERROR);
        scene.show();
    }

    public static final String CONF_PATH = "/home/unit7";
    public static final String TEXTURES_PATH = "/textures";
    private static final Logger log = Logger.getLogger(App.class);
}
