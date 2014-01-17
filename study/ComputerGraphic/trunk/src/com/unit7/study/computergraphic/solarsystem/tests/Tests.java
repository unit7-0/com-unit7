/**
 * Date:	04 янв. 2014 г.
 * File:	Tests.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.tests;

import java.lang.reflect.InvocationTargetException;

import javax.media.opengl.GL2;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.unit7.study.computergraphic.solarsystem.core.DrawnSphere;
import com.unit7.study.computergraphic.solarsystem.core.Sphere;
import com.unit7.study.computergraphic.solarsystem.core.Time;
import com.unit7.study.computergraphic.solarsystem.core.graphic.GLFrame;
import com.unit7.study.computergraphic.solarsystem.core.graphic.Renderer;
import com.unit7.study.computergraphic.solarsystem.core.graphic.drawable.Drawable;
import com.unit7.study.computergraphic.solarsystem.core.graphic.drawable.DrawableSpaceObject;
import com.unit7.study.computergraphic.solarsystem.core.graphic.drawable.DrawableSphere;
import com.unit7.study.computergraphic.solarsystem.core.interfaces.ActionObject;
import com.unit7.study.computergraphic.solarsystem.core.processors.Scene;

/**
 * @author unit7
 * 
 */
public class Tests {
    public static void main(String[] args) {
        Sphere star = new Sphere();
        DrawnSphere planet = new DrawnSphere();
        planet.setTarget(star);
        planet.setX(star.getX() - 5);
        planet.setDrawnRadius(5);
        planet.setTimeAround(2000);
        DrawableSpaceObject obj = new DrawableSphere(planet);
        
        DrawnSphere planet1 = new DrawnSphere();
        planet1.setTarget(star);
        planet1.setX(star.getX() - 10);
        planet1.setDrawnRadius(10);
        planet1.setTimeAround(3000);
        DrawableSpaceObject obj1 = new DrawableSphere(planet1);
        
        DrawnSphere planet2 = new DrawnSphere();
        planet2.setTarget(star);
        planet2.setX(star.getX() - 18);
        planet2.setDrawnRadius(18);
        planet2.setTimeAround(300);
        DrawableSpaceObject obj2 = new DrawableSphere(planet2);
        
        DrawableSpaceObject target = new DrawableSphere(star);

        Renderer renderer = new Renderer();
        renderer.addObject(target).addObject(obj).addObject(obj1).addObject(obj2);

        GLFrame frame = new GLFrame(renderer);
        frame.setVisible(true);

        Scene scene = new Scene();
        scene.setDelay(50);

        Time.getInstance().changeSpeed(0.8);
        
        scene.addObject(planet).addObject(star).addObject(planet1).addObject(planet2);
        scene.show();
    }
}
