/**
 * Date:	23 февр. 2014 г.
 * File:	CreateConnectionFrame.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.gui;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

/**
 * @author unit7
 *
 */
public abstract class UserInputFrame extends JFrame implements QueryData, AbstractFrame {
    public UserInputFrame(AtomicBoolean quit) {
        this.quit = quit;
    }
    
    public UserInputFrame() {
        this(new AtomicBoolean());
    }
    
    public AtomicBoolean getQuit() {
        return quit;
    }
    
    public void setQuit(AtomicBoolean quit) {
        this.quit = quit;
    }
    
    protected AtomicBoolean quit;
}
