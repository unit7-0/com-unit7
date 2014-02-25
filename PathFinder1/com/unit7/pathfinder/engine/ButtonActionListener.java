/**
 * Date:	22 февр. 2014 г.
 * File:	ButtonActionListner.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.engine;

import java.awt.event.ActionListener;

/**
 * @author unit7
 *
 */
public abstract class ButtonActionListener implements ActionListener {
    public ButtonActionListener(ImageMap map) {
        this.map = map;
    }
    
    private ImageMap map;
}
