/**
 * Date:	04 янв. 2014 г.
 * File:	App.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.app;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.unit7.study.computergraphic.solarsystem.engine.DrawnSphere;
import com.unit7.study.computergraphic.solarsystem.engine.DrawnSphere.Builder;
import com.unit7.study.computergraphic.solarsystem.engine.Camera;
import com.unit7.study.computergraphic.solarsystem.engine.GLCanvasMouseListener;
import com.unit7.study.computergraphic.solarsystem.engine.GLFrame;
import com.unit7.study.computergraphic.solarsystem.engine.Renderer;
import com.unit7.study.computergraphic.solarsystem.engine.Scene;
import com.unit7.study.computergraphic.solarsystem.engine.Sphere;
import com.unit7.study.computergraphic.solarsystem.engine.Time;
import com.unit7.study.computergraphic.solarsystem.engine.Utils;
import com.unit7.study.computergraphic.solarsystem.engine.drawable.DrawableSphere;

/**
 * @author unit7
 * 
 */
public class App {
    public static void main(String[] args) {
        Renderer renderer = new Renderer();
        Scene scene = new Scene();
        Time.getInstance().changeSpeed(1000000);

        /**
         * сделаем все координаты в рамках 1000x1000
         */
        double coordMul = 1000;
        double plutoDrawnRadius = 590000;
        double meruryDrawnRadius = 57910 / plutoDrawnRadius * 1000;
        double venusDrawRadius = 108000 / plutoDrawnRadius * 1000;
        double earthDrawnRadius = 149098.29 / plutoDrawnRadius * 1000;
        double marsDrawnRadius = 229232 / plutoDrawnRadius * 1000;
        double jupiterDrawnRadius = 770573.6 / plutoDrawnRadius * 1000;
        double saturnDrawnRadius = 1613325.783 / plutoDrawnRadius * 1000;
        double uranDrawnRadius = 2848938.461 / plutoDrawnRadius * 1000;
        double neptunDrawnRadius = 4503946.49 / plutoDrawnRadius * 1000;
        
        plutoDrawnRadius = 1000;
        
        // star, Sun
        // уменьшим радиус
        Sphere sun = new Sphere.Builder().setName("Sun").setAge(4.57).setRadius(69551)
                .setWeight(19891000).build();

        // planets
        DrawnSphere.Builder builder = new DrawnSphere.Builder();

     // pluto
        DrawnSphere pluto = (DrawnSphere) builder.setDrawnRadius(plutoDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(365 * 248)).setName("Pluto").setRadius(1153).setWeight(0.1305).build();
        pluto.setX(sun.getX() - coordMul);
        
        // mercury
        DrawnSphere mercury = (DrawnSphere) builder.setDrawnRadius(meruryDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(87.97)).setAge(4.6).setName("Mercury").setRadius(2439.7)
                .setWeight(3.33022).build();
        mercury.setX(sun.getX() - mercury.getDrawnRadius());

        // venus
        DrawnSphere venus = (DrawnSphere) builder.setDrawnRadius(venusDrawRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(224.7)).setName("Venus").setRadius(6051.8).setWeight(48.67).build();
        venus.setX(sun.getX() - venus.getDrawnRadius());

        // earth
        DrawnSphere earth = (DrawnSphere) builder.setDrawnRadius(earthDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(365.26)).setName("Earth").setRadius(6378.1).setWeight(59.72).build();
        earth.setX(sun.getX() - earth.getDrawnRadius());

        // mars
        DrawnSphere mars = (DrawnSphere) builder.setDrawnRadius(marsDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(687)).setName("Mars").setRadius(3396.9).setWeight(6.39).build();
        mars.setX(sun.getX() - mars.getDrawnRadius());

        // Jupiter
        DrawnSphere jupiter = (DrawnSphere) builder.setDrawnRadius(jupiterDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(4332.6)).setName("Jupiter").setRadius(71492).setWeight(18980).build();
        jupiter.setX(sun.getX() - jupiter.getDrawnRadius());

        // saturn
        DrawnSphere saturn = (DrawnSphere) builder.setDrawnRadius(saturnDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(10759.2)).setName("Saturn").setRadius(60268).setWeight(5683).build();
        saturn.setX(sun.getX() - saturn.getDrawnRadius());

        // uran
        DrawnSphere uran = (DrawnSphere) builder.setDrawnRadius(uranDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(30685.4)).setName("Uran").setRadius(25559).setWeight(868.1).build();
        uran.setX(sun.getX() - uran.getDrawnRadius());

        // neptun
        DrawnSphere neptun = (DrawnSphere) builder.setDrawnRadius(neptunDrawnRadius).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(60189)).setName("Neptun").setRadius(24764).setWeight(1024).build();
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

        double ratio = 100;
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

        // add to drawing
        renderer.addObject(sunDraw).addObject(mercuryDraw).addObject(venusDraw).addObject(earthDraw)
                .addObject(marsDraw).addObject(jupiterDraw).addObject(saturnDraw).addObject(uranDraw)
                .addObject(neptunDraw).addObject(plutoDraw);

        // add to scene
        scene.addObject(sun).addObject(mercury).addObject(venus).addObject(earth).addObject(mars).addObject(jupiter)
                .addObject(saturn).addObject(uran).addObject(neptun).addObject(pluto);

        GLFrame frame = new GLFrame(renderer);
        MouseAdapter mouseAdapter = new GLCanvasMouseListener();
        
        frame.getCanvas().addMouseWheelListener(mouseAdapter);
        frame.getCanvas().addMouseListener(mouseAdapter);
        frame.getCanvas().addMouseMotionListener(mouseAdapter);
        frame.setVisible(true);

        scene.show();
    }
    
    private static final Logger log = Logger.getLogger(App.class);
}
