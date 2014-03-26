/**
 * Date:	23 февр. 2014 г.
 * File:	ChangeConnections.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.unit7.pathfinder.graphs.Edge;
import com.unit7.pathfinder.tools.Utils;

/**
 * Форма изменения соединения
 * @author unit7
 *
 */
public class ChangeConnections extends UserInputFrame {
    public ChangeConnections(List<Edge> edges) {
        this.edges = edges;
    }
    
    /* 
     * @see com.unit7.pathfinder.gui.QueryData#getData()
     */
    @Override
    public Object getData() {
        // TODO Auto-generated method stub
        return selected;
    }

    /* 
     * @see com.unit7.pathfinder.gui.AbstractFrame#createGUI()
     */
    @Override
    public JFrame createGUI() {
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel title = new JLabel("Direkta kopplingar mellan punkter");
        DefaultListModel<String> model = new DefaultListModel<String>();

        for (Iterator<Edge> it = edges.iterator(); it.hasNext();) {
            Edge e = it.next();
            String name = e.getName();
            int weight = e.getWeight();
            model.addElement(name + " " + weight);
        }

        final JList<String> list = new JList<String>(model);
        JButton ok = new JButton("Ok");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String val = (String) list.getSelectedValue();
                if (val == null) {
                	quit.set(true);
                	return;
                }
                
                String[] vals = val.split("\\s");
                String name = null;
                String weightS = null;
                for (int i = 0; i < vals.length; ++i) {
                    val = vals[i].trim();
                    if (!"".equals(val)) {
                        if (name == null)
                            name = val; 
                        else {
                            weightS = val;
                            break;
                        }
                    }
                }
                
                try {
                    int weight = Integer.parseInt(weightS);
                    for (Iterator<Edge> it = edges.iterator(); it.hasNext();) {
                        Edge ed = it.next();
                        String name1 = ed.getName();
                        int weight1 = ed.getWeight();
                        if (name1.equals(name) && weight == weight1) {
                            selected = ed;
                            break;
                        }
                    }
                } catch (NumberFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
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

    // соединения
    private List<Edge> edges;
    // выбранное соединение
    private Edge selected;
}
