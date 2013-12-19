/**
 * Date:	15 дек. 2013 г.
 * File:	ChainCreator.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.rgz.var7.processors;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.utils.Utils;
import com.unit7.study.translationmethods.rgz.var5.processors.Creator;
import com.unit7.study.translationmethods.rgz.var7.RegExp;

/**
 * @author unit7
 * 
 */
public class ChainCreator implements Creator<Set<String>>, ActionListener {
	public ChainCreator(RegExpCreator regExpCreator) {
		this.regExpCreator = regExpCreator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame("Цепочки");
		JPanel main = new JPanel(new BorderLayout());
//		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		JTextArea area = new JTextArea(20, 30);
		JTextArea regExpArea = new JTextArea(5, 30);
		JScrollPane pane = new JScrollPane(area);
		JScrollPane regExpPane = new JScrollPane(regExpArea);

		try {
			create();
		} catch (InformationException e1) {
			JOptionPane.showMessageDialog(null, e1.getLocalizedMessage());
			return;
		}

		regExpArea.append(regExp.getExpr());
		if (chains.isEmpty()) {
			area.append("Нет цепочек по указанным условиям");
		} else {
			for (String str : chains) {
				area.append(str);
				area.append("\r\n");
			}
		}

		JMenuBar bar = Utils.createSaveFileMenu(new String[] { "Цепочка", "Выражение" }, area, regExpArea);
		frame.setJMenuBar(bar);
		
		pane.revalidate();
		regExpPane.revalidate();

		main.add(pane, BorderLayout.CENTER);
		main.add(regExpPane, BorderLayout.PAGE_END);

		frame.getContentPane().add(main);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		
		Utils.centreFrame(null, frame);
		frame.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.translationmethods.rgz.var5.processors.Creator#create()
	 */
	@Override
	public Set<String> create() throws InformationException {
		try {
			regExp = regExpCreator.create();
		} catch (Exception e) {
			throw new InformationException(e.getLocalizedMessage());
		}

		int minLen = regExp.getMin();
		int maxLen = regExp.getMax();
		Tree.setTerms(regExp.getTerminals());
		Tree tree = Tree.parseTree(regExp.getExpr());
		List<String> answer = generate(tree, regExp.getMax());
		chains = new HashSet<String>(answer);
		Set<String> result = new HashSet<String>();
		for (String chain : chains) {
			if (!(chain.length() < minLen || chain.length() > maxLen))
				result.add(chain);
		}

		chains = result;
		return chains;
	}

	protected List<String> generate(Tree tree, int maxLen) {
		if (levelTwoCache.containsKey(tree)) {
			return levelTwoCache.get(tree);
		}

		List<String> result = new ArrayList<String>();
		// терминальная строка должна присутствовать обязательно
		if (tree.isMonolith()) {
			result.add(tree.getNode());
		} else if (tree.isStub()) {
			// заглушка - используется только как узел, реальные цепочки
			// получаются из детей
			List<String> chains = new ArrayList<String>();
			chains.addAll(generate(tree.getChilds().get(0), maxLen));
			for (int i = 1; i < tree.getChilds().size(); ++i) {
				Tree tr = tree.getChilds().get(i);
				List<String> tmp = null;

				if (!tr.isMonolith() && !tr.isStub()) {
					if (levelOneCache.containsKey(tr.getName())) {
						tmp = levelOneCache.get(tr.getName());
					} else {
						tmp = generate(tr, maxLen);
						levelOneCache.put(tr.getName(), tmp);
					}
				} else {
					tmp = generate(tr, maxLen);
				}

				List<String> newChains = new ArrayList<String>();
				for (String str : tmp) {
					for (String chain : chains) {
						if (str.length() + chain.length() > maxLen)
							continue;

						StringBuilder builder = new StringBuilder(chain)
								.append(str);
						newChains.add(builder.toString());
					}
				}

				chains = newChains;
			}

			if (!tree.isOnlyOne())
				chains.add("");

			result.addAll(chains);
		} else {
			// возможен только один вариант
			if (tree.isOnlyOne()) {
				String node = tree.getNode();
				for (int i = 0; i < node.length(); ++i) {
					result.add(String.valueOf(node.charAt(i)));
				}
			} else {
				// возможно множество вариантов из заданных терминалов
				result.add("");
				String node = tree.getNode();
				for (int len = 1; len <= maxLen; ++len) {
					for (int i = 0; i < node.length(); ++i) {
						List<String> gen = new ArrayList<String>();
						gen.add(String.valueOf(node.charAt(i)));
						for (int j = 1; j < len; ++j) { // TODO optimize
							List<String> tmp = new ArrayList<String>();
							for (int k = 0; k < node.length(); ++k) { // TODO check it
								for (String str : gen) {
									StringBuilder builder = new StringBuilder(
											str);
									builder.append(node.charAt(k));
									tmp.add(builder.toString());
								}
							}
							gen = tmp;
						}

						result.addAll(gen);
					}
				}
			}
		}

		levelTwoCache.put(tree, result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.unit7.study.translationmethods.rgz.var5.processors.Creator#getCreated
	 * ()
	 */
	@Override
	public Set<String> getCreated() throws Exception {
		if (chains == null)
			throw new InformationException(Creator.EMPTY_PARAMETER);

		return chains;
	}

	private Set<String> chains;
	private RegExp regExp;
	private RegExpCreator regExpCreator;

	private Map<String, List<String>> levelOneCache = new HashMap<String, List<String>>();
	private Map<Tree, List<String>> levelTwoCache = new HashMap<Tree, List<String>>();
}
