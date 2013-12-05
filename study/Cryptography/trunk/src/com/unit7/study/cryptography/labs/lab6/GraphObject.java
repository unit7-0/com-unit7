package com.unit7.study.cryptography.labs.lab6;

import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;

public class GraphObject implements VerificationData {
	public int[][] getG() {
		return G;
	}

	public void setG(int[][] g) {
		G = g;
	}

	public int[][] getH() {
		return H;
	}

	public int[][] getF() {
		return F;
	}
	
	public void setH(int[][] h) {
		H = h;
	}

	public void setF(int[][] f) {
		F = f;
	}

	private int[][] G;
	private int[][] H;
	private int[][] F;
}
