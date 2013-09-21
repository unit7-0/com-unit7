package com.unit7.study.translationmethods.labs.lab1;

import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainInterface extends JFrame {
    public MainInterface(String title) {
        createGUI();
    }
    
    public void createGUI() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Файл");
        Menu helpMenu = new Menu("Помощь");
        MenuItem exit = new MenuItem("Выход");
        MenuItem defaultGrammar = new MenuItem("Грамматика по умолчанию");
        MenuItem newGrammar =  new MenuItem("Задать грамматику");
        
        fileMenu.add(newGrammar);
        fileMenu.add(defaultGrammar);
        fileMenu.add(exit);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        setMenuBar(menuBar);
        
        JPanel panel = new JPanel();
        
        JLabel terminalHelp = new JLabel("Терминальные символы:\t");
        JLabel notTerminalHelp = new JLabel("Нетерминальные символы:\t");
        JLabel targetSymbolHelp = new JLabel("Целевой символ:\t");
        JLabel lenHelp = new JLabel("Длина цепочек(max):\t");
        JLabel ruleHelp = new JLabel("Правила формирования:\t");
        
        JTextField terminalField = new JTextField();
        JTextField notTerminalField = new JTextField();
        JTextField targetSymbolField = new JTextField();
        JTextField lenField = new JTextField();
        // TODO create rule field
        
        JButton buildButton = new JButton("Построить");
        
        terminalHelp.setLabelFor(terminalField);
        notTerminalHelp.setLabelFor(notTerminalField);
        targetSymbolHelp.setLabelFor(targetSymbolField);
        lenHelp.setLabelFor(lenField);
        // TODO add label for ruleField
        
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 30));
        inputPanel.add(terminalHelp);
        inputPanel.add(terminalField);
        inputPanel.add(notTerminalHelp);
        inputPanel.add(notTerminalField);
        inputPanel.add(targetSymbolHelp);
        inputPanel.add(targetSymbolField);
        inputPanel.add(lenHelp);
        inputPanel.add(lenField);
        
        
        panel.setLayout(new GridLayout(3, 1, 10, 30));
        panel.add(inputPanel);
        
        // TODO add rule to content
        
        panel.add(buildButton);
        
        content = panel;
        
        getContentPane().add(content);
    }
    
    public MainInterface() {
        this("Lab1");
    }
    
    public JPanel getContent() {
        return content;
    }

    public void setContent(JPanel content) {
        this.content = content;
    }

    private JPanel content;
}
