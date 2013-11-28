package com.unit7.study.cryptography.labs.lab4.forms;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
				
				JFrame table = new GameTable(count).createGUI();
				table.setVisible(true);
			}
		});
		
		controls.add(gamersField);
		controls.add(acceptButton);

		content.add(controls);
		getContentPane().add(content);
		setTitle("Покер");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);
		setResizable(false);
		return this;
	}

}
