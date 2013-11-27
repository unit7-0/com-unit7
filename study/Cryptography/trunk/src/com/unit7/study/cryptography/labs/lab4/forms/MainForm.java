package com.unit7.study.cryptography.labs.lab4.forms;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainForm extends AbstractForm {
	@Override
	public JFrame createGUI() {
		JPanel content = new JPanel();
		JPanel controls = new JPanel(new GridLayout(2, 1, 10, 10));

		final JTextField gamersField = new JTextField(20);
		JButton acceptButton = new JButton("Сдать");

		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String gamers = gamersField.getText();
				int count = 0;
				
				try {
					count = Integer.parseInt(gamers);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(MainForm.this, "Введите число");
					return;
				}
				
				if (count < 2 || count > 23) {
					JOptionPane.showMessageDialog(MainForm.this, "Неверное количество игроков");
					return;
				}
				
				
			}
		});

		controls.add(gamersField);
		controls.add(acceptButton);

		content.add(controls);
		getContentPane().add(content);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		return this;
	}

}
