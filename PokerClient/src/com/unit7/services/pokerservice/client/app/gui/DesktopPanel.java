/**
 * Date:	21 дек. 2013 г.
 * File:	DesktopPanel.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.app.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import com.unit7.services.pokerservice.client.model.Card;
import com.unit7.services.pokerservice.client.model.LightweightGamer;

/**
 * @author unit7
 *
 */
public class DesktopPanel extends JPanel {
    public DesktopPanel(List<LightweightGamer> gamers) {        
        GridBagConstraints c = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
        Iterator<LightweightGamer> it = gamers.iterator();
        c.gridx = 1;
        addHorizontal(this, c, it);
        c.gridx = 4;
        c.gridy = 1;
        addVertical(this, c, it);
        c.gridx = 1;
        c.gridy = 4;
        addHorizontal(this, c, it);
        c.gridx = 0;
        c.gridy = 1;
    }
    
    private void addHorizontal(JPanel root, GridBagConstraints c, Iterator<LightweightGamer> it) {
        if (!it.hasNext())
            return;
        
        LightweightGamer gamer = it.next();
        GamerPanel panel = new GamerPanel(gamer);
        root.add(panel, c);
        
        if (!it.hasNext())
            return;
        
        gamer = it.next();
        panel = new GamerPanel(gamer);
        c.gridx += 1;
        root.add(panel, c);
        
        if (!it.hasNext())
            return;
        
        gamer = it.next();
        panel = new GamerPanel(gamer);
        c.gridx += 1;
        root.add(panel, c);
        
        c.gridx = 2;
        c.gridy = 2;
        add(prikupPanel);
    }
    
    private void addVertical(JPanel root, GridBagConstraints c, Iterator<LightweightGamer> it) {
        if (!it.hasNext())
            return;
        
        LightweightGamer gamer = it.next();
        GamerPanel panel = new GamerPanel(gamer);
        root.add(panel, c);
        
        if (!it.hasNext())
            return;
        
        gamer = it.next();
        panel = new GamerPanel(gamer);
        c.gridy += 2;
        root.add(panel, c);
    }
    
    public void setPrikup(List<Card> cards) {
        prikupPanel.setPrikup(cards);
    }
    
    private PrikupPanel prikupPanel = new PrikupPanel();
}
