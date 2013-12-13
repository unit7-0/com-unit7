/**
 * Date:	11 дек. 2013 г.
 * File:	MainForm.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.labs.lab4;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.lab3.interfaces.AutomateApp;
import com.unit7.study.translationmethods.labs.lab4.interfaces.State;
import com.unit7.study.translationmethods.labs.lab4.interfaces.impl.AutomateAppImpl;
import com.unit7.study.translationmethods.labs.lab4.interfaces.impl.DataBuilder;

/**
 * @author unit7
 *
 */
public class MainForm extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new MainForm();
        frame.setVisible(true);
    }

    public MainForm() {
        JPanel content = new JPanel(new GridBagLayout());//new GridLayout(3, 1, 10, 10));
        JPanel top = new JPanel(new GridLayout(3, 2, 10, 10));
        final Box statesPanel = new Box(BoxLayout.PAGE_AXIS);// new JPanel(new
                                                             // FlowLayout());
        JPanel controls = new JPanel();

        JLabel termsLabel = new JLabel("Список терминалов:");
        final JTextField termsField = new JTextField();

        JLabel chainLabel = new JLabel("Цепочка:");
        final JTextField chainField = new JTextField();

        JLabel startStateLabel = new JLabel("Начальное состояние:");
        final JTextField startStateField = new JTextField();

        JLabel finalStateLabel = new JLabel("Конечное состояние: ");
        final JTextField finalStateField = new JTextField();

//        top.add(termsLabel);
//        top.add(termsField);

        top.add(chainLabel);
        top.add(chainField);

        top.add(startStateLabel);
        top.add(startStateField);

        top.add(finalStateLabel);
        top.add(finalStateField);

        JPanel states = buildStatesPanel();
        addRemoveButton(statesPanel, states);

        JButton check = new JButton("Проверить");
        JButton add = new JButton("Добавить состояние");
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String terms = termsField.getText();
                String chain = chainField.getText();
                String startState = startStateField.getText();
                String finalState = finalStateField.getText();

                Map<String, String> params = new HashMap<String, String>();
                Component[] comps = statesPanel.getComponents();

                int k = 0;
                int k2 = 0;
                for (int i = 0; i < comps.length; ++i) {
                    if (comps[i] instanceof JPanel) {
                        // toAdd Panel
                        JPanel comp = (JPanel) comps[i];
                        // main
                        comp = (JPanel) comp.getComponents()[0];
                        // states
                        comp = (JPanel) comp.getComponents()[0];
                        // start and end
                        Component[] comps1 = comp.getComponents();

                        JPanel startPanel = (JPanel) comps1[0];
                        JPanel endPanel = (JPanel) comps1[2];
                        
                        Component[] startComps = startPanel.getComponents();
                        Component[] endComps = endPanel.getComponents();
                        
                        for (int p = 0; p < startComps.length; ++p) {
                            if (startComps[p] instanceof JTextField) {
                                JTextField stateField = (JTextField) startComps[p];
                                int j = p + 1;
                                while (j < startComps.length && !(startComps[j] instanceof JTextField)) {
                                    ++j;
                                }

                                JTextField jumpField = (JTextField) startComps[j];
                                j = j + 1;

                                while (j < startComps.length && !(startComps[j] instanceof JTextField)) {
                                    ++j;
                                }

                                JTextField stackField = (JTextField) startComps[j];

                                p = j;

                                params.put(AutomateApp.PARAM_STATE_BEG + k, stateField.getText());
                                params.put(AutomateApp.PARAM_JUMP_BEG + k, jumpField.getText());
                                params.put(AutomateApp.PARAM_STACK_BEG + k++, stackField.getText());
                            }
                        }
                        
                        for (int p = 0; p < endComps.length; ++p) {
                            if (endComps[p] instanceof JTextField) {
                                JTextField stateField = (JTextField) endComps[p];
                                int j = p + 1;
                                while (j < endComps.length && !(endComps[j] instanceof JTextField)) {
                                    ++j;
                                }

                                JTextField stackField = (JTextField) endComps[j];
                              
                                p = j;

                                params.put(AutomateApp.PARAM_TO_STATE_BEG + k2, stateField.getText());
                                params.put(AutomateApp.PARAM_STACK_TO_BEG + k2++, stackField.getText());
                            }
                        }
                    }
                }

                params.put(AutomateApp.PARAM_TERMINALS, terms);
                params.put(AutomateApp.PARAM_CHAIN, chain);
                params.put(AutomateApp.PARAM_START_STATE, startState);
                params.put(AutomateApp.PARAM_FINAL_STATE, finalState);

                String result = check(params);
                JOptionPane.showMessageDialog(MainForm.this, result);
            }
        });

        final JScrollPane pane = new JScrollPane(statesPanel);
//        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel states = buildStatesPanel();
                addRemoveButton(statesPanel, states);
                statesPanel.validate();
                statesPanel.repaint();
                pane.revalidate();
                pane.repaint();
            }
        });

        controls.add(check);
        controls.add(add);

        GridBagConstraints c = new GridBagConstraints();
        
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        content.add(top, c);
        
        c.gridwidth = 3;
        c.gridheight = 5;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = c.gridheight;
        content.add(pane, c);
        
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 6;
        c.gridheight = 1;
        content.add(controls, c);

        getContentPane().add(content);
        setTitle("unit7©");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    private String check(Map<String, String> params) {
        try {
//            String terms = builder.parseTerminals(params);
            String chain = builder.parseChain(params);
            Map<String, State> states = builder.parseStates(params);
            State startState = builder.parseStartState(params, states);
            List<State> finalState = builder.parseFinalState(params, states);

            app = new AutomateAppImpl(chain, startState);

            if (app.execute()) {
                return ACCEPTED_CHAIN  + "\r\n" + log(app.getLog());
            } else
                return DECLINED_CHAIN + "\r\n" + log(app.getLog());

            // return AutomateApp.STATE_IS_NOT_FINAL;
        } catch (InformationException e) {
            return e.getLocalizedMessage() + "\r\n" + (app == null ? "" : log(app.getLog()));
        } finally {
            
        }
    }

    private JPanel buildStatesPanel() {
        JPanel main = new JPanel();
        
        JPanel start = new JPanel(new GridLayout(1, 6, 15, 15));
        JPanel end = new JPanel(new GridLayout(1, 4, 15, 15));
        
        JPanel states = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        start.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        end.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        states.setBorder(BorderFactory.createTitledBorder(""));
        
        main.add(states);

        JLabel toState = new JLabel(" -> ");
        
        c.gridwidth = 2;
        states.add(start, c);
        c.gridwidth = 1;
        states.add(toState, c);
        c.gridwidth = 1;
        states.add(end, c);
        
        JLabel stateLabel = new JLabel("Начальное: ");
        JTextField stateField = new JTextField();

        JLabel jumpLabel = new JLabel("Входной символ: ");
        JTextField jumpField = new JTextField();

        JLabel toStateLabel = new JLabel("На стеке: ");
        JTextField toStateField = new JTextField();

        start.add(stateLabel);
        start.add(stateField);

        start.add(jumpLabel);
        start.add(jumpField);

        start.add(toStateLabel);
        start.add(toStateField);

        toStateLabel = new JLabel("Конечное: ");
        toStateField = new JTextField();
        
        JLabel stackLabel = new JLabel("На стеке");
        JTextField stackField = new JTextField();
        
        end.add(toStateLabel);
        end.add(toStateField);
        
        end.add(stackLabel);
        end.add(stackField);
        
        return main;
    }

    private void addRemoveButton(final JComponent root, JPanel relative) {
        JPanel toAdd = new JPanel(new GridBagLayout());
        JPanel removePanel = new JPanel();
        JButton remove = new JButton("Удалить");

        removePanel.add(remove);

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        
        toAdd.add(relative, c);
        c.gridwidth = 1;
        toAdd.add(removePanel, c);

//        toAdd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1)));
        
        final Component comp = root.add(toAdd);

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                root.remove(comp);
                root.repaint();
                pack();
            }
        });
    }

    private String log(List<String> log) {
        StringBuilder builder = new StringBuilder("\r\n[ ");
        for (String str : log) {
            if (builder.length() > 4)
                builder.append("\r\n");
            
            builder.append(str);
        }
        
        return builder.append(" ]").toString();
    }
    
    public static final String ACCEPTED_CHAIN = "Цепочка принята";
    public static final String DECLINED_CHAIN = "Цепочка отвергнута";

    private DataBuilder builder = new DataBuilder();
    private AutomateApp app;
}
