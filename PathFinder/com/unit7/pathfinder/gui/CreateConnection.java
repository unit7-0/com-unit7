/**
 * Date:	23 февр. 2014 г.
 * File:	CreateConnection.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.unit7.pathfinder.tools.Utils;

/**
 * @author unit7
 * 
 */
public class CreateConnection extends UserInputFrame {
    public CreateConnection(String name) {
        super();
        this.name = name;
    }
    
    /**
     * @param quit
     */
    public CreateConnection(AtomicBoolean quit) {
        super(quit);
        // TODO Auto-generated constructor stub
    }

    /*
     * @see com.unit7.pathfinder.gui.QueryData#getData()
     */
    @Override
    public Object getData() {
        return new Object[] { name, time };
    }

    /*
     * @see com.unit7.pathfinder.gui.AbstractFrame#createGUI()
     */
    @Override
    public JFrame createGUI() {
        JPanel content = new JPanel();

        JLabel title = new JLabel("Enter the name of the connection and the time traveling");
        JLabel nameL = new JLabel("Name:");
        JLabel timeL = new JLabel("Time: ");

        final JTextField name = new JTextField(20);
        final JTextField time = new JTextField(20);

        if (this.name != null) {
            name.setText(this.name);
            name.setEnabled(false);
        }
        
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateConnection.this.name = name.getText().trim();
                CreateConnection.this.time = time.getText().trim();
                quit.set(true);
            }
        });
        
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit.set(true);
            }
        });

        JPanel label = new JPanel();
        JPanel namep = new JPanel();
        JPanel timep = new JPanel();
        JPanel controls = new JPanel();
        
        label.add(title);
        namep.add(nameL);
        namep.add(name);
        timep.add(timeL);
        timep.add(time);
        
        controls.add(ok);
        controls.add(cancel);
        
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        
        content.add(label);
        content.add(namep);
        content.add(timep);
        content.add(controls);
        
        setContentPane(content);
        pack();
        Utils.centreFrame(null, this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        return this;
    }

    private String name;
    private String time;
}
