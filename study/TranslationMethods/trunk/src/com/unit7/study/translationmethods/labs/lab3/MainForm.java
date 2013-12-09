package com.unit7.study.translationmethods.labs.lab3;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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
import com.unit7.study.translationmethods.labs.lab3.interfaces.State;
import com.unit7.study.translationmethods.labs.lab3.interfaces.impl.AutomateAppImpl;
import com.unit7.study.translationmethods.labs.lab3.interfaces.impl.DataBuilder;

public class MainForm extends JFrame {
	public static void main(String[] args) {
		JFrame frame = new MainForm();
		frame.setVisible(true);
	}

	public MainForm() {
		JPanel content = new JPanel(new GridLayout(3, 1, 10, 10));
		JPanel top = new JPanel(new GridLayout(4, 2, 10, 10));
		final Box statesPanel = new Box(BoxLayout.Y_AXIS);// new JPanel(new
															// FlowLayout());
		JPanel controls = new JPanel();

		JLabel termsLabel = new JLabel("Список терминалов:");
		final JTextField termsField = new JTextField(20);

		JLabel chainLabel = new JLabel("Цепочка:");
		final JTextField chainField = new JTextField(30);

		JLabel startStateLabel = new JLabel("Начальное состояние:");
		final JTextField startStateField = new JTextField(10);

		JLabel finalStateLabel = new JLabel("Конечное состояние: ");
		final JTextField finalStateField = new JTextField(10);

		top.add(termsLabel);
		top.add(termsField);

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
				for (int i = 0; i < comps.length; ++i) {
					if (comps[i] instanceof JPanel) {
						JPanel comp = (JPanel) comps[i];
						comp = (JPanel) comp.getComponents()[0];
						comp = (JPanel) comp.getComponents()[0];
						Component[] comps1 = comp.getComponents();

						for (int p = 0; p < comps1.length; ++p) {

							if (comps1[p] instanceof JTextField) {
								JTextField stateField = (JTextField) comps1[p];
								int j = p + 1;
								while (j < comps1.length
										&& !(comps1[j] instanceof JTextField)) {
									++j;
								}

								JTextField jumpField = (JTextField) comps1[j];
								j = j + 1;

								while (j < comps1.length
										&& !(comps1[j] instanceof JTextField)) {
									++j;
								}

								JTextField toStateField = (JTextField) comps1[j];

								p = j;

								params.put(AutomateApp.PARAM_STATE_BEG + k,
										stateField.getText());
								params.put(AutomateApp.PARAM_JUMP_BEG + k,
										jumpField.getText());
								params.put(
										AutomateApp.PARAM_TO_STATE_BEG + k++,
										toStateField.getText());
							}
						}
					}
				}

				params.put(AutomateApp.PARAM_TERMINALS, terms);
				params.put(AutomateApp.PARAM_CHAIN, chain);
				params.put(AutomateApp.PARAM_START_STATE, startState);
				params.put(AutomateApp.PARAM_FINAL_STATE, finalState);

				JOptionPane.showMessageDialog(MainForm.this, check(params));
			}
		});

		final JScrollPane pane = new JScrollPane(statesPanel);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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

		content.add(top);
		content.add(pane);
		content.add(controls);

		getContentPane().add(content);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}

	private String check(Map<String, String> params) {
		try {
			String terms = builder.parseTerminals(params);
			String chain = builder.parseChain(params);
			Map<String, State> states = builder.parseStates(params);
			State startState = builder.parseStartState(params, states);
			State finalState = builder.parseFinalState(params, states);

			AutomateApp app = new AutomateAppImpl(terms, chain, startState,
					finalState);

			if (app.execute()) {
				return "Ok";
			}

			return AutomateApp.STATE_IS_NOT_FINAL;
		} catch (InformationException e) {
			return e.getLocalizedMessage();
		}
	}

	private JPanel buildStatesPanel() {
		JPanel main = new JPanel();
		JPanel states = new JPanel(new GridLayout(1, 6, 15, 15));
		main.add(states);

		JLabel stateLabel = new JLabel("Состояние: ");
		JTextField stateField = new JTextField(10);

		JLabel jumpLabel = new JLabel("Переход: ");
		JTextField jumpField = new JTextField(10);

		JLabel toStateLabel = new JLabel("В состояние: ");
		JTextField toStateField = new JTextField(10);

		states.add(stateLabel);
		states.add(stateField);

		states.add(jumpLabel);
		states.add(jumpField);

		states.add(toStateLabel);
		states.add(toStateField);

		return main;
	}

	private void addRemoveButton(final JComponent root, JPanel relative) {
		JPanel toAdd = new JPanel(new GridLayout(1, 2, 10, 10));
		JPanel removePanel = new JPanel();
		JButton remove = new JButton("Удалить");

		removePanel.add(remove);

		toAdd.add(relative);
		toAdd.add(removePanel);

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

	private DataBuilder builder = new DataBuilder();
}
