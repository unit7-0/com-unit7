/**
 * Date:	04 янв. 2014 г.
 * File:	GLFrame.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;

/**
 * @author unit7
 * 
 */
public class GLFrame extends JFrame {
    public GLFrame() {
        setTitle("Solar System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public GLFrame(GLEventListener renderer) {
        this();
        
        // canvas
        canvas = new GLCanvas();
        canvas.addGLEventListener(renderer);

        animator = new Animator(canvas);
        createGUI();
    }

    public JFrame createGUI() {
        canvas.setIgnoreRepaint(true);
        canvas.setSize(1000, 700);

        // frame props
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().add(canvas, BorderLayout.CENTER);
        setSize(getPreferredSize());

        // animation
        animator.start();

        // closing window
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                animator.stop();
            }
        });

        return this;
    }

    public GLCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(GLCanvas canvas) {
        this.canvas = canvas;
    }

    public Animator getAnimator() {
        return animator;
    }

    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    private GLCanvas canvas;
    private Animator animator;
}
