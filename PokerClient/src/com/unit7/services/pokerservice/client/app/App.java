/**
 * Date:	17 дек. 2013 г.
 * File:	App.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.app;

import javax.swing.JFrame;

import com.unit7.services.pokerservice.client.app.gui.MainForm;
import com.unit7.services.pokerservice.client.engine.Controller;
import com.unit7.services.pokerservice.client.engine.GUIErrorHandler;

/**
 * @author unit7
 *
 */
public class App {
    public static void main(String[] args) {
        configure();
        JFrame app = new MainForm().createGUI();
        app.setVisible(true);
    }
    
    public static void configure() {
        Controller.getInstance().setErrorHandler(new GUIErrorHandler());
    }
}
