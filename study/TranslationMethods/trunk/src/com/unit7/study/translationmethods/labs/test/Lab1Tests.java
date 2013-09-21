package com.unit7.study.translationmethods.labs.test;

import java.awt.event.WindowEvent;

import com.jogamp.newt.event.WindowAdapter;
import com.unit7.study.translationmethods.labs.lab1.MainInterface;

import junit.framework.TestCase;

public class Lab1Tests extends TestCase {
    public void testInterface() {
        MainInterface app = new MainInterface();
        
        app.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quite = true;
                super.windowClosing(e);
            }
        });
        
        app.setVisible(true);
        app.pack();
        while (!quite) {
        }
    }
    
    private boolean quite = false;
}
