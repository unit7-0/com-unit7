/**
 * Date:	22 февр. 2014 г.
 * File:	FrameApp.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sun.util.locale.LocaleUtils;

import com.unit7.pathfinder.engine.Holder;
import com.unit7.pathfinder.engine.ImageMap;
import com.unit7.pathfinder.engine.NamedHolder;
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
        JScrollPane pane = new JScrollPane(controls);
        createCommands(controls);

        imagePanel = new ImageMapPanel(mapHolder);
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(pane, BorderLayout.NORTH);
        content.add(imagePanel, BorderLayout.CENTER);

        imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        imagePanel.addMouseListener(mapHolder.getObj());

        setContentPane(content);

        // setSize(400, 300);
        pack();
        // setSize(getWidth(), 300);
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
        final JMenuItem save = new JMenuItem("Save");
        final JMenuItem saveAs = new JMenuItem("Save as...");
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
                mapHolder.getObj().setState(ImageMap.NEW_PLACE_STATE);
            }
        };
        ActionListener newConnectionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mapHolder.getObj().addConnection(null);
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
                        mapHolder.getObj().showConnections(null);
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
                        mapHolder.getObj().changeConnection(null);
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
                        mapHolder.getObj().findPath(null);
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
                while (mapHolder.getObj().isChanged()) {
                    if (showSaveQuestion()) {
                        save.doClick();
                    } else {
                        break;
                    }
                }

                JFileChooser chooser = new JFileChooser();
                int res = chooser.showOpenDialog(FrameApp.this);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    try {
                        mapHolder.getObj().clear();
                        mapHolder.getObj().setImagePath(f.getAbsolutePath());
                        mapHolder.getObj().loadImage();
                        BufferedImage image = mapHolder.getObj().getImage();
                        FrameApp.this.imagePanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
                        FrameApp.this.pack();
                        FrameApp.this.repaint();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(FrameApp.this, "Error while image reading!");
                    }
                }
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (mapHolder.getObj().isChanged()) {
                    if (showSaveQuestion()) {
                        save.doClick();
                    } else {
                        break;
                    }
                }

                JFileChooser chooser = new JFileChooser();
                int res = chooser.showOpenDialog(FrameApp.this);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    String name = f.getName();
                    String path = f.getAbsolutePath().substring(0, f.getAbsolutePath().indexOf(name));
                    try {
                        NamedHolder<ImageMap> holder = Utils.readObject(path, name);
                        mapHolder.setName(holder.getName());
                        mapHolder.setObj(holder.getObj());
                        mapHolder.getObj().setImagePanel(imagePanel);
                        mapHolder.getObj().setChanged(false);
                        imagePanel.addMouseListener(mapHolder.getObj());
                        try {
                            if (mapHolder.getObj().getImagePath() != null) {
                                mapHolder.getObj().loadImage();
                                BufferedImage image = mapHolder.getObj().getImage();
                                FrameApp.this.imagePanel.setPreferredSize(new Dimension(image.getWidth(), image
                                        .getHeight()));
                                FrameApp.this.pack();
                                FrameApp.this.repaint();
                            }
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(FrameApp.this, "Error while image readin!");
                        }
                    } catch (ClassNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(FrameApp.this, "Format not matched!");
                    }
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mapHolder.getName() == null) {
                    saveAs.doClick();
                    return;
                }

                Utils.saveObject(mapHolder, dir);
                mapHolder.getObj().setChanged(false);
            }
        });

        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int res = 0;
                res = chooser.showSaveDialog(FrameApp.this);
                if (res != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                File f = chooser.getSelectedFile();

                mapHolder.setName(f.getName());
                dir = f.getAbsolutePath().substring(0, f.getAbsolutePath().indexOf(f.getName()));
                save.doClick();
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

    public boolean showSaveQuestion() {
        int res = JOptionPane.showConfirmDialog(FrameApp.this, "Save the changes?");
        if (res == JOptionPane.YES_OPTION) {
            return true;
        }

        return false;
    }

    public JPanel createPanelForComponent(JComponent comp, String title) {
        JPanel panel = new JPanel();
        if (title != null)
            panel.setBorder(BorderFactory.createTitledBorder(title));

        panel.add(comp);
        return panel;
    }

    private NamedHolder<ImageMap> mapHolder = new NamedHolder<ImageMap>(new ImageMap());
    private ImageMapPanel imagePanel;
    private String dir = "";
}