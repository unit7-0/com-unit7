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

        // star, Sun
        // уменьшим радиус
        Sphere sun = new Sphere.Builder().setName("Sun").setAge(4.57).setRadius(69551)
                .setWeight(BigInteger.valueOf(19891).multiply(BigInteger.TEN.pow(26))).build();

        // planets
        DrawnSphere.Builder builder = new DrawnSphere.Builder();

        // mercury
        DrawnSphere mercury = (DrawnSphere) builder.setDrawnRadius(57910).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(87.97)).setAge(4.6).setName("Mercury").setRadius(2439.7)
                .setWeight(BigInteger.valueOf(333022).multiply(BigInteger.TEN.pow(18))).build();
        mercury.setX(sun.getX() - mercury.getDrawnRadius());

        // venus
        DrawnSphere venus = (DrawnSphere) builder.setDrawnRadius(108000).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(224.7)).setName("Venus").setRadius(6051.8).build();
        venus.setX(sun.getX() - venus.getDrawnRadius());

        // earth
        DrawnSphere earth = (DrawnSphere) builder.setDrawnRadius(149098.29).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(365.26)).setName("Earth").setRadius(6378.1).build();
        earth.setX(sun.getX() - earth.getDrawnRadius());

        // mars
        DrawnSphere mars = (DrawnSphere) builder.setDrawnRadius(229232).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(687)).setName("Mars").setRadius(3396.9).build();
        mars.setX(sun.getX() - mars.getDrawnRadius());

        // Jupiter
        DrawnSphere jupiter = (DrawnSphere) builder.setDrawnRadius(770573.6).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(4332.6)).setName("Jupiter").setRadius(71492).build();
        jupiter.setX(sun.getX() - jupiter.getDrawnRadius());

        // saturn
        DrawnSphere saturn = (DrawnSphere) builder.setDrawnRadius(1613325.783).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(10759.2)).setName("Saturn").setRadius(60268).build();
        saturn.setX(sun.getX() - saturn.getDrawnRadius());

        // uran
        DrawnSphere uran = (DrawnSphere) builder.setDrawnRadius(2848938.461).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(30685.4)).setName("Uran").setRadius(25559).build();
        uran.setX(sun.getX() - uran.getDrawnRadius());

        // neptun
        DrawnSphere neptun = (DrawnSphere) builder.setDrawnRadius(4503946.49).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(60189)).setName("Neptun").setRadius(24764).build();
        neptun.setX(sun.getX() - neptun.getDrawnRadius());

        // pluto
        DrawnSphere pluto = (DrawnSphere) builder.setDrawnRadius(5900000).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(365 * 248)).setName("Pluto").setRadius(1153).build();
        pluto.setX(sun.getX() - pluto.getDrawnRadius());

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
