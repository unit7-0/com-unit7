/**
 * file: Test.java
 * date: 16 дек. 2013 г.
 * 
 * author: Zajcev V.
 */
package com.unit7.study.translationmethods.labs.test;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test app = new Test();
		String expr = "00(1+2)0((1+2)0(1+2)((0+1)1(1+2)))((0+1+2)1222((1+2)01))";
		Tree.setTerms("012");
		Tree tree = Tree.parseTree(expr);
		app.printLeftToRight(tree, "", "");
	}

	public void printLeftToRight(Tree tree, String spaces, String parent) {
		for (Tree tr: tree.getChilds()) {
			printLeftToRight(tr, spaces + "\t", tree.getName());
		}
		
		System.out.println(spaces  + "name: " + tree.getName() + " parent: " + parent + " node: " + tree.getNode() + " stub: " + tree.isStub() + " monolith: " + tree.isMonolith());
	}
}
