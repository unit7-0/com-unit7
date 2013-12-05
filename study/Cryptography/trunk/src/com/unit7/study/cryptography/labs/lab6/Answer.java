package com.unit7.study.cryptography.labs.lab6;

import java.util.List;

import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;

public class Answer implements VerificationData {
	public int[][] getF() {
		return F;
	}

	public void setF(int[][] f) {
		F = f;
	}

	public List<int[][]> getIndicies() {
		return indicies;
	}

	public void setIndicies(List<int[][]> indicies) {
		this.indicies = indicies;
	}

	private int[][] F;
	private List<int[][]> indicies;
}
