/**
 * Date:	04 янв. 2014 г.
 * File:	App.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.unit7.study.computergraphic.solarsystem.core.Camera;
import com.unit7.study.computergraphic.solarsystem.core.DrawnSphere;
import com.unit7.study.computergraphic.solarsystem.core.Sphere;
import com.unit7.study.computergraphic.solarsystem.core.Time;
import com.unit7.study.computergraphic.solarsystem.core.Utils;
import com.unit7.study.computergraphic.solarsystem.core.graphic.Background;
import com.unit7.study.computergraphic.solarsystem.core.graphic.GLFrame;
import com.unit7.study.computergraphic.solarsystem.core.graphic.Renderer;
import com.unit7.study.computergraphic.solarsystem.core.graphic.drawable.DrawableSpaceObject;
import com.unit7.study.computergraphic.solarsystem.core.graphic.drawable.DrawableSphere;
import com.unit7.study.computergraphic.solarsystem.core.processors.GravitationSystem;
import com.unit7.study.computergraphic.solarsystem.core.processors.KeyboardListener;
import com.unit7.study.computergraphic.solarsystem.core.processors.MouseCameraListener;
import com.unit7.study.computergraphic.solarsystem.core.processors.MouseCoordGetter;
import com.unit7.study.computergraphic.solarsystem.core.processors.Scene;

/**
 * @author unit7
 * 
 */
public class App {
    public App() {
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
        double moondDrawnRadius = 9840.4 / plutoDrawnRadius * coordMul;

        plutoDrawnRadius = coordMul;

        double radiusMul = 0.0001;
        double sunRadius = 69551;
        
        // star, Sun
        // уменьшим радиус
        Sphere sun = new Sphere.Builder().setName("Sun").setAge(4.57).setRadius(sunRadius * radiusMul).setWeight(19891000).build();

        // planets
        DrawnSphere.Builder builder = new DrawnSphere.Builder();

        // pluto
        DrawnSphere pluto = (DrawnSphere) builder.setDrawnRadius(plutoDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(365 * 248)).setName("Pluto").setRadius(1153 * radiusMul).setWeight(0.1305)
                .build();
        pluto.setX(sun.getX() - coordMul);

        // mercury
        DrawnSphere mercury = (DrawnSphere) builder.setDrawnRadius(meruryDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(87.97)).setAge(4.6).setName("Mercury").setRadius(2439.7 * radiusMul)
                .setWeight(3.33022).build();
        mercury.setX(sun.getX() - mercury.getDrawnRadius());

        // venus
        DrawnSphere venus = (DrawnSphere) builder.setDrawnRadius(venusDrawRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(224.7)).setName("Venus").setRadius(6051.8 * radiusMul).setWeight(48.67)
                .build();
        venus.setX(sun.getX() - venus.getDrawnRadius());

        // earth
        DrawnSphere earth = (DrawnSphere) builder.setDrawnRadius(earthDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(365.26)).setName("Earth").setRadius(6378.1 * radiusMul).setWeight(59.72)
                .build();
        earth.setX(sun.getX() - earth.getDrawnRadius());

        // mars
        DrawnSphere mars = (DrawnSphere) builder.setDrawnRadius(marsDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(687)).setName("Mars").setRadius(3396.9 * radiusMul).setWeight(6.39).build();
        mars.setX(sun.getX() - mars.getDrawnRadius());

        // Jupiter
        DrawnSphere jupiter = (DrawnSphere) builder.setDrawnRadius(jupiterDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(4332.6)).setName("Jupiter").setRadius(71492 * radiusMul).setWeight(18980)
                .build();
        jupiter.setX(sun.getX() - jupiter.getDrawnRadius());

        // saturn
        DrawnSphere saturn = (DrawnSphere) builder.setDrawnRadius(saturnDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(10759.2)).setName("Saturn").setRadius(60268 * radiusMul).setWeight(5683)
                .build();
        saturn.setX(sun.getX() - saturn.getDrawnRadius());

        // uran
        DrawnSphere uran = (DrawnSphere) builder.setDrawnRadius(uranDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(30685.4)).setName("Uranus").setRadius(25559 * radiusMul).setWeight(868.1)
                .build();
        uran.setX(sun.getX() - uran.getDrawnRadius());

        // neptune
        DrawnSphere neptun = (DrawnSphere) builder.setDrawnRadius(neptunDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(60189)).setName("Neptune").setRadius(24764 * radiusMul).setWeight(1024)
                .build();
        neptun.setX(sun.getX() - neptun.getDrawnRadius());
        
        // moon
		DrawnSphere moon = (DrawnSphere) builder.setDrawnRadius(moondDrawnRadius)
				.setTarget(earth).setTimeAround(Utils.daysToMilliseconds(27.32)).setName("Moon").setRadius(1737.4 * radiusMul)
				.setWeight(1).build();
		moon.setX(earth.getX() - moon.getDrawnRadius());

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
        DrawableSphere moonDraw = new DrawableSphere(moon);

        double ratio = 1;
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
        moonDraw.setRatio(ratio);

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
        objs.add(moonDraw);

        objects = objs;
        
        Background background = new Background();
        
        // add to drawing
        renderer.addObject(background).addObject(sunDraw).addObject(mercuryDraw).addObject(venusDraw).addObject(earthDraw)
                .addObject(marsDraw).addObject(jupiterDraw).addObject(saturnDraw).addObject(uranDraw)
                .addObject(neptunDraw).addObject(plutoDraw).addObject(moonDraw);

        // add to scene
        scene.addObject(sun).addObject(mercury).addObject(venus).addObject(earth).addObject(mars).addObject(jupiter)
                .addObject(saturn).addObject(uran).addObject(neptun).addObject(pluto).addObject(moon);

        /*
         * mercury.setVy(-0.000000711); venus.setVy(-0.000000600);
         * earth.setVy(-0.000000500);
         * grSystem.addObject(sun).addObject(mercury).
         * addObject(venus).addObject(earth);.addObject(mars).addObject(jupiter)
         * .
         * addObject(saturn).addObject(uran).addObject(neptun).addObject(pluto);
         */

        GLFrame frame = new GLFrame(renderer);
        MouseAdapter mouseAdapter = new MouseCameraListener();
        java.awt.event.KeyAdapter keyAdapter = new KeyboardListener(this);
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
        
        background.setWidth(frame.getCanvas().getWidth());
        background.setHeight(frame.getCanvas().getHeight());
        
//        final CoordPanel coordPanel = new CoordPanel(coordGetter);
//        frame.getContentPane().add(coordPanel);
        frame.setVisible(true);
        
//        Timer timer = new Timer();
  /*      timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                coordPanel.paintComponent(coordPanel.getGraphics());
            }
        }, 0, 100);
  */      
        
        // new Thread(grSystem).start();
        Logger.getRootLogger().setLevel(Level.ERROR);
        new Thread(scene).start();
        
//        Camera.getInstance().setTarget(jupiter);
        Camera.getInstance().setDistance(20);
    }
    
    public boolean isShowOrbits() {
		return showOrbits;
	}

	public void showOrbits(boolean show) {
        for (DrawableSpaceObject obj : objects) {
            if (obj instanceof DrawableSphere) {
                ((DrawableSphere) obj).setShowOrbit(show);
            }
        }
        
        showOrbits = show;
    }

	public static void main(String[] args) {
		new App();
	}
	
	private List<DrawableSpaceObject> objects;
    
    private boolean showOrbits;
    
    public static final String CONF_PATH = "C:/temp";
    public static final String TEXTURES_PATH = "/textures";
    
    private static final Logger log = Logger.getLogger(App.class);
}
