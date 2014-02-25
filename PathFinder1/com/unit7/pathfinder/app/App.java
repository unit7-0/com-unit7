/**
 * Date:	22 февр. 2014 г.
 * File:	App.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.app;

import javax.swing.JFrame;

import com.unit7.pathfinder.gui.FrameApp;

/**
 * @author unit7
 *
 */
public class App {
    public static void main(String[] args) {
        JFrame frame = new FrameApp().createGUI();
        frame.setVisible(true);
    }
}
