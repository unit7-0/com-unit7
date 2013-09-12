package com.unit7.study.computergraphic.labs.lab2;

 import java.awt.Frame;
 import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.awt.GLCanvas;
 
 public class JavaDia implements Runnable {
     static Thread displayT = new Thread(new JavaDia());
     static boolean bQuit = false;
 
     public static void main(String[] args) {
         displayT.start();
     }
 
     public void run() {
         Frame frame = new Frame("Jogl 3D Shape/Rotation");
         GLCanvas canvas = new GLCanvas();
         canvas.addGLEventListener(new JavaRenderer(128));
         frame.add(canvas);
         frame.setSize(640, 480);
         //frame.setUndecorated(true);
         int size = frame.getExtendedState();
         size |= Frame.MAXIMIZED_BOTH;
         //frame.setExtendedState(size);
 
         frame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                 bQuit = true;
             }
         });
         frame.setVisible(true);
 //      frame.show();
         canvas.requestFocus();
         while( !bQuit ) {
             canvas.display();
         }
     }
 }