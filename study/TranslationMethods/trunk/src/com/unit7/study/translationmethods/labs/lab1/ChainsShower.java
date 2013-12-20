package com.unit7.study.translationmethods.labs.lab1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.unit7.study.translationmethods.labs.lab2.TreeBuilder;
import com.unit7.study.translationmethods.labs.lab2.TreeFrame;
import com.unit7.study.translationmethods.labs.utils.Utils;

public class ChainsShower extends JFrame {
	private int min;
	
	public ChainsShower(final Map<String, String> exprs, final String target,
			int lens, int min) {
		super("Цепочки");
		this.min = min;
		this.expressions = new ConcurrentHashMap<String, String>() {
			{
				for (Map.Entry<String, String> entry : exprs.entrySet()) {
					put(entry.getKey(), entry.getValue());
				}
			}
		};
		this.sourceExpr = new HashMap<String, String>() {
			{
				for (Map.Entry<String, String> entry : exprs.entrySet()) {
					put(entry.getKey(), entry.getValue());
				}
			}
		};

		this.maxLen = lens;
		recursiveRemover.remove(expressions);

		JTextArea area = new JTextArea();
		String[] vars = exprs.get(target).split(GrammarRules.GRAMMAR_DELIMETER);
		// for each rule we call doBuild and get some chain
		Set<String> finalAnswer = new HashSet<String>();
		for (String var : vars) {
			// if there is a repeats...
			finalAnswer.addAll(doBuild(var, 0));
			// log.put(var, target);
		}

		JPanel panel = new JPanel(new GridLayout((int) Math.ceil(finalAnswer
				.size() / 15.0), 15, 10, 10));
		JScrollPane pane = new JScrollPane(panel);
		for (String res : finalAnswer) {
			if (!res.isEmpty() && res.length() >= min) {
				area.append(res);
				area.append("\r\n");
				final JLabel label = new JLabel(res);
				label.setBackground(Color.WHITE);
				label.setFont(Font.getFont(Font.MONOSPACED));
				label.setOpaque(true);
				label.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseClicked(MouseEvent e) {
						String chain = label.getText();
						/*
						 * List<String> path = new ArrayList<String>(); String
						 * begin = chain; do { path.add(begin); } while ();
						 * 
						 * Collections.reverse(path); StringBuilder result = new
						 * StringBuilder(); for (String comp : path) {
						 * result.append(comp).append("->"); }
						 * 
						 * result.delete(result.length() - 2, result.length());
						 * JFrame window = new JFrame(); window.add(new
						 * JLabel(result.toString()));
						 * window.setDefaultCloseOperation
						 * (JFrame.DISPOSE_ON_CLOSE); window.setVisible(true);
						 * window.pack();
						 */

						TreeFrame frame = new TreeFrame(sourceExpr, target,
								chain);
						frame.setBuilder(new TreeBuilder());
						frame.showContent();
						Utils.centreFrame(ChainsShower.this, frame);
						frame.setVisible(true);
					}
				});

				panel.add(label);
				// log.put(res, var);
			}
		}

		setJMenuBar(Utils.createSaveFileMenu(new String[] { "Цепочки" }, area));
		
		setContentPane(pane);
		setSize(new Dimension(400, 300));
		Utils.centreFrame(null, this);
		try {
			FileOutputStream out = new FileOutputStream("/home/unit7/log.txt");
			PrintWriter writer = new PrintWriter(out);
			writer.println(log.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * build chains TODO improve asymptotic
	 * 
	 * @param expr
	 * @param pos
	 * @return
	 */
	private List<String> doBuild(String expr, int pos) {
		StringBuilder result = new StringBuilder(expr.substring(0, pos));
		List<String> totalResult = new ArrayList<String>();
		// step by step through expression if we met notTerminal then buid all
		// possible chains
		if (pos > maxLen)
			return totalResult;
		for (int i = pos; i < expr.length(); ++i) {
			if (GrammarRules.isTerminal(expr.charAt(i))) {
				if (result.length() + 1 > maxLen) {
					return totalResult;
				}

				result.append(expr.charAt(i));
			} else if (GrammarRules.isNotTerminal(expr.charAt(i))) {
				// possible chains
				String name = nameToolz.getName(expr.substring(i));
				String[] possibleChains = expandChain(name);
				// each chain may present as before = result[pos..i - 1],
				// current = one of chains[]
				// and after = before + each of chains[] + remain
				// expression(expr)
				for (String possible : possibleChains) {
					if (possible.equals(GrammarRules.GRAMMAR_EMPTY)) {
						// add current chain to result if we find the empty
						// symbol
						// and at the end we must add all chains to the set
						// for construct final answer without repeats
						// totalResult.add(result.toString());
						List<String> anotherChains = doBuild(result.toString()
								+ expr.substring(i + name.length()),
								result.length());
						totalResult.addAll(anotherChains);
						// log.put(expr, result.toString());
						continue;
					}

					List<String> chains = null;
					// TODO make it normal (crazy)
					try {
						chains = doBuild(
								result.toString() + possible
										+ expr.substring(i + name.length()),
								result.length());
					} catch (StackOverflowError err) {
						continue;
					}

					// log all manipulations
					for (String chain : chains) {
						// log.put(chain, expr);
					}

					totalResult.addAll(chains);
				}

				return totalResult;
			}
		}

		totalResult.add(result.toString());
		return totalResult;
	}

	private String[] expandChain(String chain) {
		return expressions.get(chain).split(GrammarRules.GRAMMAR_DELIMETER);
	}

	private int maxLen;
	private RecursionRemover recursiveRemover = new LeftRecursionRemover();
	private NameToolz nameToolz = new DigitNameToolz();
	private Map<String, String> expressions;
	private Map<String, String> sourceExpr;

	private static final Logger log = Logger.getLogger(ChainsShower.class
			.getName());
}
