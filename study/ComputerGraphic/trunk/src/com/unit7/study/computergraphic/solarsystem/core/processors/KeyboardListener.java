/**
 * Date:	11 янв. 2014 г.
 * File:	GLCanvasKeyboardListener.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.core.processors;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.apache.log4j.Logger;

import com.unit7.study.computergraphic.solarsystem.app.App;
import com.unit7.study.computergraphic.solarsystem.core.Camera;

/**
 * @author unit7
 * 
 */
public class KeyboardListener extends KeyAdapter {
	public KeyboardListener(App app) {
		this.app = app;
	}
	
    @Override
    public void keyPressed(KeyEvent e) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("pressed key [ %s ] code [ %d ]", e.getKeyChar(), e.getKeyCode()));
        }
        int key = e.getKeyCode();
        double d = 10;
        switch (key) {
        case KeyEvent.VK_LEFT:
            Camera.getInstance().addEyeX(-d);
            Camera.getInstance().addCenterX(-d);
            break;
        case KeyEvent.VK_RIGHT:
            Camera.getInstance().addEyeX(d);
            Camera.getInstance().addCenterX(d);
            break;
        case KeyEvent.VK_UP:
            Camera.getInstance().addEyeY(d);
            Camera.getInstance().addCenterY(d);
            break;
        case KeyEvent.VK_DOWN:
            Camera.getInstance().addEyeY(-d);
            Camera.getInstance().addCenterY(-d);
            break;
        case KeyEvent.VK_PLUS:
            Camera.getInstance().addEyeZ(d);
            break;
        case KeyEvent.VK_MINUS:
            Camera.getInstance().addEyeZ(-d);
            break;
        case KeyEvent.VK_SPACE:
            app.showOrbits(!app.isShowOrbits());
//            app.showInfo(!app.isShowInfo());
            break;
        case KeyEvent.VK_I:
            app.showInfo(!app.isShowInfo());
            break;
        }
    //    super.keyPressed(e);
    }

    private App app;
    
    private static final Logger log = Logger.getLogger(KeyboardListener.class);
}
