/**
 * Date:	22 февр. 2014 г.
 * File:	FrameApp.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import sun.util.locale.LocaleUtils;

import com.unit7.pathfinder.tools.Utils;

/**
 * @author unit7
 * 
 */
public class FrameApp extends JFrame implements AbstractFrame {

    /*
     * (non-Javadoc)
     * 
     * @see com.unit7.pathfinder.gui.AbstractFrame#createGUI()
     */
    @Override
    public JFrame createGUI() {
        JPanel controls = new JPanel();
        createCommands(controls);

        JPanel imagePanel = new ImageMapPanel(map);
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(controls, BorderLayout.NORTH);
        content.add(imagePanel, BorderLayout.CENTER);

        imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        imagePanel.addMouseListener(map);

        setContentPane(content);

        // setSize(400, 300);
        pack();
        setSize(getWidth(), 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Utils.centreFrame(null, this);
        return this;
    }

    private void createCommands(JPanel panel) {
        // create menu
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu operations = new JMenu("Operations");

        JMenuItem create = new JMenuItem("Create");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save as...");
        JMenuItem exit = new JMenuItem("Exit");

        JMenuItem newConnection = new JMenuItem("New connection");
        JMenuItem newPlace = new JMenuItem("New place");
        JMenuItem showConnection = new JMenuItem("Show connection");
        JMenuItem changeConnection = new JMenuItem("Change connection");
        JMenuItem findPath = new JMenuItem("Find path");

        file.add(create);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(exit);

        operations.add(newConnection);
        operations.add(newPlace);
        operations.add(showConnection);
        operations.add(changeConnection);
        operations.add(findPath);

        menuBar.add(file);
        menuBar.add(operations);

        setJMenuBar(menuBar);

        // listeners
        ActionListener newPlaceListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.setState(ImageMap.NEW_PLACE_STATE);
            }
        };
        ActionListener newConnectionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        map.addConnection(null);
                    }
                }).start();
            }
        };
        ActionListener showConnectionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        map.showConnections(null);
                    }
                }).start();
            }
        };
        ActionListener changeConnectionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        map.changeConnection(null);
                    }
                }).start();
            }
        };
        ActionListener findPathListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        map.findPath(null);
                    }
                }).start();
            }
        };

        // add lsteners to operations
        newPlace.addActionListener(newPlaceListener);
        newConnection.addActionListener(newConnectionListener);
        showConnection.addActionListener(showConnectionListener);
        changeConnection.addActionListener(changeConnectionListener);
        findPath.addActionListener(findPathListener);

        // add listeners to file
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int res = chooser.showOpenDialog(FrameApp.this);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    try {
                        BufferedImage image = ImageIO.read(f);
                        map.setImage(image);
                        FrameApp.this.repaint();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        // create buttons
        JButton newPlaceButton = new JButton("New place");
        JButton newConnectionButton = new JButton("New connection");
        JButton changeConnectionButton = new JButton("Change connection");
        JButton showConnectionButton = new JButton("Show connection");
        JButton findPathButton = new JButton("Find path");

        // add listeners
        newPlaceButton.addActionListener(newPlaceListener);
        newConnectionButton.addActionListener(newConnectionListener);
        showConnectionButton.addActionListener(showConnectionListener);
        changeConnectionButton.addActionListener(changeConnectionListener);
        findPathButton.addActionListener(findPathListener);

        panel.add(newPlaceButton);
        panel.add(newConnectionButton);
        panel.add(showConnectionButton);
        panel.add(changeConnectionButton);
        panel.add(findPathButton);
    }

    public JPanel createPanelForComponent(JComponent comp, String title) {
        JPanel panel = new JPanel();
        if (title != null)
            panel.setBorder(BorderFactory.createTitledBorder(title));

        panel.add(comp);
        return panel;
    }

    private ImageMap map = new ImageMap();
}
