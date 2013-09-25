package com.unit7.study.translationmethods.labs.lab1;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

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
		MenuItem newGrammar = new MenuItem("Задать грамматику");

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

		JPanel panel = new JPanel(new BorderLayout());

		JLabel terminalHelp = new JLabel("Терминальные символы:\t");
		JLabel notTerminalHelp = new JLabel("Нетерминальные символы:\t");
		JLabel targetSymbolHelp = new JLabel("Целевой символ:\t");
		JLabel lenHelp = new JLabel("Длина цепочек(max):\t");
		JLabel ruleHelp = new JLabel("Правила формирования:\t");

		final JTextField terminalField = new JTextField();
		final JTextField notTerminalField = new JTextField();
		final JTextField targetSymbolField = new JTextField();
		final JTextField lenField = new JTextField();
		final JPanel rulePanel = new JPanel();

		final JButton buildButton = new JButton("Построить");
		JPanel buildPanel = new JPanel();
		buildPanel.add(buildButton, BorderLayout.CENTER);
		buildButton.setEnabled(false);
		buildButton.addActionListener(new BuildListener(new JComponent[] {
				terminalField, notTerminalField, targetSymbolField, lenField,
				rulePanel }));

		final JButton clearButton = new JButton("Очистить");
		JPanel clearPanel = new JPanel();
		clearPanel.add(clearButton);
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				terminalField.setText("");
				notTerminalField.setText("");
				targetSymbolField.setText("");
				lenField.setText("");
			}
		});

		JButton prepareButton = new JButton("Подготовить");
		JPanel preparePanel = new JPanel();
		preparePanel.add(prepareButton);
		prepareButton.addActionListener(new PrepareListener(new JComponent[] {
				terminalField, notTerminalField, targetSymbolField, lenField,
				rulePanel, clearButton }));

		JButton resetButton = new JButton("Сбросить");
		JPanel resetPanel = new JPanel();
		resetPanel.add(resetButton);
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rulePanel.removeAll();
				rulePanel.validate();
				rulePanel.updateUI();
				terminalField.setEnabled(true);
				notTerminalField.setEnabled(true);
				targetSymbolField.setEnabled(true);
				lenField.setEnabled(true);
				clearButton.setEnabled(true);
				MainInterface.this.pack();
			}
		});

		terminalField.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getSource() instanceof JTextField
						&& evt.getSource() == terminalField
						&& evt.getPropertyName().equals("enabled")) {
							buildButton.setEnabled(!terminalField.isEnabled());
				}
			}
		});

		terminalHelp.setLabelFor(terminalField);
		notTerminalHelp.setLabelFor(notTerminalField);
		targetSymbolHelp.setLabelFor(targetSymbolField);
		lenHelp.setLabelFor(lenField);
		ruleHelp.setLabelFor(rulePanel);

		JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 30));
		JPanel rulePanelOut = new JPanel(new GridLayout(1, 2, 5, 10));
		rulePanelOut.add(ruleHelp);
		rulePanelOut.add(rulePanel);

		inputPanel.add(terminalHelp);
		inputPanel.add(terminalField);
		inputPanel.add(notTerminalHelp);
		inputPanel.add(notTerminalField);
		inputPanel.add(targetSymbolHelp);
		inputPanel.add(targetSymbolField);
		inputPanel.add(lenHelp);
		inputPanel.add(lenField);

		panel.add(inputPanel, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(1, 4, 10, 5));
		controls.add(preparePanel);
		controls.add(resetPanel);
		controls.add(clearPanel);
		controls.add(buildPanel);

		panel.add(controls, BorderLayout.SOUTH);
		panel.add(rulePanelOut, BorderLayout.CENTER);

		setContentPane(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}

	public MainInterface() {
		this("Lab1");
	}

	public Map<String, String> getExpressions() {
		return expressions;
	}

	public void swapContent(int mode) {
		Container content = getContentPane();
		if (mode == AUTO_MODE) {

		} else if (mode == HAND_MODE) {

		}
	}

	public static final int AUTO_MODE = 0;
	public static final int HAND_MODE = 1;

	private Map<String, String> expressions = new HashMap();
}
