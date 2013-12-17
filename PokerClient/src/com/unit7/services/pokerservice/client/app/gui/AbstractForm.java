/**
 * Date:	17 дек. 2013 г.
 * File:	AbstractForm.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.app.gui;

import javax.swing.JFrame;

/**
 * @author unit7
 *
 */
public abstract class AbstractForm extends JFrame {
    public abstract JFrame createGUI();
}
