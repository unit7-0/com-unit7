/**
 * file: Tree.java
 * date: 16 дек. 2013 г.
 * 
 * author: Zajcev V.
 */
package com.unit7.study.translationmethods.labs.test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Tree {
	public static Tree parseTree(String expr) {
		Tree root = new Tree();
		root.stub = true;
		root.name = "root";
		
		Tree prev = root;
		Tree cur = new Tree();
		for (int i = 0; i < expr.length(); ++i) {
			if (terms.indexOf(expr.charAt(i)) != -1) {
				StringBuilder builder = new StringBuilder();
				while (i < expr.length() && terms.indexOf(expr.charAt(i)) != -1) {
					builder.append(expr.charAt(i));
					++i;
				}
				
				--i;
				cur.name = builder.toString();
				cur.node = builder.toString();
				cur.monolith = true;
				prev.addChild(cur);
				cur = new Tree();
			} else if (expr.charAt(i) == '(') {
				int r = getLastBracket(expr, i);
				Tree tr = build(expr.substring(i, r + 1));
				i = r;
				prev.addChild(tr);
			} else {
				System.out.println(String.format("Something wrong [index: %s]", i));
			}
		}
		
		return root;
	}
	
	protected static int getLastBracket(String expr, int p) {
		int r = p;
		Deque<Integer> deq = new ArrayDeque<Integer>();
		for (int i = p; i < expr.length(); ++i) {
			if (expr.charAt(i) == '(')
				deq.push(1);
			else if (expr.charAt(i) == ')') {
				deq.pop();
			}
			
			if (deq.isEmpty()) {
				r = i;
				break;
			}
		}
		
		return r;
	}
	
	protected static Tree build(String expr) {
		Tree tree = new Tree();
		if (isSimple(expr, 1)) {
			StringBuilder builder = new StringBuilder();
			for (int i = 1; i < expr.length() - 1; ++i) {
				if (terms.indexOf(expr.charAt(i)) != -1) {
					builder.append(expr.charAt(i));
				}
			}
			
			tree.node = builder.toString();
			tree.name = builder.toString();
		} else {
			tree.stub = true;
			if (term(expr)) {
				List<String> strs = split(expr);
				for (String str : strs) {
					if (onlyTerm(str)) {
						Tree tr = new Tree();
						tr.monolith = true;
						tr.node = str;
						tr.name = str;
						tree.addChild(tr);
					} else {
						tree.addChild(build(str));
					}
				}
			} else {
				List<String> strs = split(expr);
				for (String str : strs) {
					tree.addChild(build(str));
				}
			}
			tree.name = expr;
		}
		
		return tree;
	}
	
	protected static boolean onlyTerm(String expr) {
		for (int i = 0; i < expr.length(); ++i) {
			if (terms.indexOf(expr.charAt(i)) == -1)
				return false;
		}
		
		return true;
	}
	
	protected static List<String> split(String expr) {
		boolean inExp = false;
		int count = 0;
		ArrayList<String> strs = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < expr.length() - 1; ++i) {
			char c = expr.charAt(i);
			if (c == '(' && !inExp) {
				if (builder.length() != 0) {
					strs.add(builder.toString());
					builder.delete(0, builder.length());
				}
				
				inExp = true;
				count = 1;
				builder.append(expr.charAt(i));
			} else if (c == '(' && inExp) {
				count += 1;
				builder.append('(');
			} else if (c == ')' && inExp) {
				builder.append(')');
				if (count == 1) {
					inExp = false;
					strs.add(builder.toString());
					builder.delete(0, builder.length());
				} else {
					count -= 1;
				}
			} else {
				builder.append(c);
			}
		}
		
		if (builder.length() != 0)
			strs.add(builder.toString());
		
		return strs;
	}
	
	protected static boolean term(String expr) {
		Deque<Integer> deq = new ArrayDeque<Integer>();
		deq.push(1);
		for (int i = 1; i < expr.length(); ++i) {
			if (expr.charAt(i) == '(')
				deq.push(1);
			else if (expr.charAt(i) == ')') {
				deq.pop();
			}
			
			if (deq.size() == 1) {
				return true;
			}
		}
		
		return false;
	}
	
	protected static boolean isSimple(String expr, int i) {
		return expr.indexOf('(', i) == -1;
	}
	
	protected void addChild(Tree tree) {
		this.childs.add(tree);
	}
	
	public static void setTerms(String str) {
		terms = str;
	}
	
	public List<Tree> getChilds() {
		return childs;
	}
	
	public String getNode() {
		return node;
	}

	public boolean isMonolith() {
		return monolith;
	}

	public boolean isStub() {
		return stub;
	}

	public static String getTerms() {
		return terms;
	}

	public String getName() {
		return name;
	}

	private List<Tree> childs = new ArrayList<Tree>();
	private String node;
	private boolean monolith;
	private boolean stub;
	private String name;
	
	private static String terms;
}
