package com.unit7.study.translationmethods.labs.lab1;

import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChainsShower extends JFrame {
	public ChainsShower(Map<String, String> exprs, String target, int lens) {
		super("Цепочки");
		this.expressions = exprs;

		JTextArea area = new JTextArea();
		JScrollPane pane = new JScrollPane(area);
		String[] vars = exprs.get(target).split(GrammarRules.GRAMMAR_DELIMETER);
		// for each rule we call doBuild and get some chain
		for (String var : vars) {
			String[] nextRes = doBuild(var, 0);
			for (String res : nextRes) {
				area.append(res + "\n");
			}
		}

		setContentPane(pane);
		pack();
	}

	/**
	 * build chains
	 * 
	 * @param expr
	 * @param pos
	 * @return
	 */
	private String[] doBuild(String expr, int pos) {
    	StringBuilder result = new StringBuilder(expr.substring(0, pos - 1));
    	// step by step through expression if we met notTerminal then buid all possible chains
    	for (int i = pos; i < expr.length(); ++i) {
    		if (GrammarRules.isTerminal(expr.charAt(i))) {
    			result.append(expr.charAt(i));
    		} else if (GrammarRules.isNotTerminal(expr.charAt(i))) {
    			// possible chains
    			String[] possibleChains = expandChain(expr.substring(i, i + 1));
    			// each chain may present as before = result[pos..i - 1], 
    			// current = one of chains[]
    			// and after = before + each of chains[] + remain expression(expr)
    			for (String possible : possibleChains) {
    				String[] chains = doBuild(result.toString() + possible + expr.substring(i + 1), result.length());
    			}
    		}
    	}
    }

	private String[] expandChain(String chain) {
		return expressions.get(chain).split(GrammarRules.GRAMMAR_DELIMETER);
		// TODO may be rule expand to empty symbol(delimiter) then array is
		// null, check and FIX it
	}

	public String[] buildChains(String target, int len) {
		String exprs = expressions.get(target);
		String[] vars = exprs.split(GrammarRules.GRAMMAR_DELIMETER);
		String expr = ""; // ????

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < expr.length(); ++i) {
			if (GrammarRules.isTerminal(expr.charAt(i))) {
				result.append(expr.charAt(i));
			} else if (GrammarRules.isNotTerminal(expr.charAt(i))) {
				result.append(buildChains(expr.substring(i, i + 1), len));
			}
		}

		return result.toString();
	}

	private Map<String, String> expressions;
}
