/**
 * Date:	04 янв. 2014 г.
 * File:	App.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.app;

import java.math.BigInteger;

import com.unit7.study.computergraphic.solarsystem.engine.DrawnSphere;
import com.unit7.study.computergraphic.solarsystem.engine.DrawnSphere.Builder;
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
        Sphere sun = new Sphere.Builder().setName("Sun").setAge(4.57).setRadius(695510000)
                .setWeight(BigInteger.valueOf(19891).multiply(BigInteger.TEN.pow(26))).build();

        // planets
        DrawnSphere.Builder builder = new DrawnSphere.Builder();

        // mercury
        DrawnSphere mercury = (DrawnSphere) builder.setDrawnRadius(57910).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(87.97)).setAge(4.6).setName("Mercury").setRadius(2439.7)
                .setWeight(BigInteger.valueOf(333022).multiply(BigInteger.TEN.pow(18))).build();
        mercury.setX(sun.getX() - mercury.getDrawnRadius());

        // pluto
        DrawnSphere pluto = (DrawnSphere) builder.setDrawnRadius(5900000).setTarget(sun)
                .setTimeAround(Utils.daysToMilliseconds(365 * 248)).setName("Pluto").setRadius(1153).build();
        pluto.setX(sun.getX() - pluto.getDrawnRadius());

        DrawableSphere sunDraw = new DrawableSphere(sun);
        DrawableSphere mercuryDraw = new DrawableSphere(mercury);
        DrawableSphere plutoDraw = new DrawableSphere(pluto);

        double ratio = 0.004;
        sunDraw.setRatio(ratio);
        mercuryDraw.setRatio(ratio);
        plutoDraw.setRatio(ratio);

        // add to drawing
        renderer.addObject(sunDraw).addObject(mercuryDraw).addObject(plutoDraw);

        // add to scene
        scene.addObject(sun).addObject(mercury).addObject(pluto);

        GLFrame frame = new GLFrame(renderer);
        frame.setVisible(true);

        scene.show();
    }
}
