package com.unit7.study.translationmethods.labs.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import com.unit7.study.translationmethods.labs.chains.Container;

public class Utils {
	public static void centreFrame(JFrame main, JFrame child) {
		int x = 0;
		int y = 0;

		if (main == null) {
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			x = d.width / 2 - child.getWidth() / 2;
			y = d.height / 2 - child.getHeight() / 2;
		} else {
			x = main.getX() + main.getWidth() / 2 - child.getWidth() / 2;
			y = main.getY() + main.getHeight() / 2 - child.getHeight() / 2;
		}

		child.setLocation(x, y);
	}

	public static boolean validateContainer(Container container) {
		return container.getChainPos() >= container.getChain().length()
				&& container.getRulePos() >= container.getRule().length();
	}

	public static boolean validateContainers(List<Container> containers) {
		if (containers == null)
			return true;

		for (Container cont : containers) {
			if (!validateContainer(cont))
				return false;
		}

		return true;
	}

	public static JMenuBar createSaveFileMenu(final String[] titles,
			final Object... args) {

		// final JTextArea[] areas = (JTextArea[]) args;

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem item = new JMenuItem("Save");

		menu.add(item);
		menuBar.add(menu);
		
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int res = chooser.showSaveDialog(null);
				if (res == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getAbsolutePath();
					File file = new File(path);
					PrintWriter writer = null;
					try {
						writer = new PrintWriter(new FileWriter(file));
						for (int i = 0; i < args.length; ++i) {
							JTextArea area = (JTextArea) args[i];
							String title = titles[i];
							writer.println(title + ":\r\n");
							writer.println(area.getText());
							writer.println();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						if (writer != null)
							writer.close();
					}
				}
			}
		});

		return menuBar;
	}
}
