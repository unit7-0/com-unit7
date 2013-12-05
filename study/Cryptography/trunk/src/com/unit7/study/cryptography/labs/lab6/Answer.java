package com.unit7.study.cryptography.labs.lab6;

import java.util.List;
import java.util.Map;

import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;
import com.unit7.study.cryptography.tools.Pair;

public class Answer implements VerificationData {
	public int[][] getH() {
		return H;
	}

	public void setH(int[][] h) {
		this.H = h;
	}

	public Map<Pair<Integer, Integer>, Integer> getIndicies() {
		return indicies;
	}

	public void setIndicies(Map<Pair<Integer, Integer>, Integer> indicies) {
		this.indicies = indicies;
	}

	public List<Pair<Integer, Integer>> getSwaped() {
        return swaped;
    }

    public void setSwaped(List<Pair<Integer, Integer>> swaped) {
        this.swaped = swaped;
    }

    public CoderInfo getCoder() {
        return coder;
    }

    public void setCoder(CoderInfo coder) {
        this.coder = coder;
    }

    private int[][] H;
    private CoderInfo coder;
	private List<Pair<Integer, Integer>> swaped;
	private Map<Pair<Integer, Integer>, Integer> indicies;
}
