/**
 * Date:	23 февр. 2014 г.
 * File:	ShowPath.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.unit7.pathfinder.graphs.Edge;
import com.unit7.pathfinder.tools.Utils;

/**
 * @author unit7
 *
 */
public class ShowPath extends UserInputFrame {
    public ShowPath(String[] sPath) {
        this.sPath = sPath;
    }
    
    /* 
     * @see com.unit7.pathfinder.gui.QueryData#getData()
     */
    @Override
    public Object getData() {
        // TODO Auto-generated method stub
        return null;
    }

    /* 
     * @see com.unit7.pathfinder.gui.AbstractFrame#createGUI()
     */
    @Override
    public JFrame createGUI() {
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel title = new JLabel("Path between points");
        DefaultListModel<String> model = new DefaultListModel<String>();

        for (String s : sPath) {
            model.addElement(s);
        }

        JList<String> list = new JList<String>(model);
        JButton ok = new JButton("Ok");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit.set(true);
            }
        });

        JPanel controls = new JPanel();
        controls.add(ok);
        
        content.add(title);
        content.add(list);
        content.add(controls);
        
        setContentPane(content);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Utils.centreFrame(null, this);
        return this;
    }

    private String[] sPath;
}
