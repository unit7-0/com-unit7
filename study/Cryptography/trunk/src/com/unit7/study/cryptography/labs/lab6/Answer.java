package com.unit7.study.cryptography.labs.lab6;

import java.util.List;
import java.util.Map;

import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;
import com.unit7.study.cryptography.tools.Pair;

public class Answer implements VerificationData {
	public int[][] getF() {
		return F;
	}

	public void setF(int[][] f) {
		F = f;
	}

	public Map<Pair<Integer, Integer>, Integer> getIndicies() {
		return indicies;
	}

	public void setIndicies(Map<Pair<Integer, Integer>, Integer> indicies) {
		this.indicies = indicies;
	}

	private int[][] F;
	private Map<Pair<Integer, Integer>, Integer> indicies;
}
