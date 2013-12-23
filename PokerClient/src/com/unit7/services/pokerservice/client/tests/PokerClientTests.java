/**
 * Date:	22 дек. 2013 г.
 * File:	PokerClientTests.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.services.pokerservice.client.tests;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Test;

import com.unit7.services.pokerservice.client.resources.Resources;

/**
 * @author unit7
 *
 */
public class PokerClientTests {
    public void loadResources() {
        try {
            Class.forName(Resources.class.getName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        PokerClientTests tests = new PokerClientTests();
        tests.loadResources();
        tests.panel();
    }
    
    @Test
    public void panel() {
    	JFrame fr = new JFrame();
    	
    	JPanel main = new JPanel(new GridLayout(2, 1, 10, 10));
    	final JPanel main1 = new JPanel(null);
    	main.setSize(10, 10);
    	main1.setBackground(Color.RED);
    	main1.setSize(10, 10);
    	
    	main.add(main1);
    	
    	JPanel main2 = new JPanel();
    	JButton resize = new JButton("resize");
    	
    	resize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main1.setSize(100, 100);
			}
		});
    	
    	main2.add(resize);
    	main.add(main2);
    	main.setBackground(Color.BLUE);
    	
    	fr.getContentPane().add(main);
    	fr.setSize(10, 10);
    	
//    	fr.pack();
    	fr.setVisible(true);
    }
}
