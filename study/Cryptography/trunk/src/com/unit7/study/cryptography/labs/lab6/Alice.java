package com.unit7.study.cryptography.labs.lab6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab2.RSACoder;
import com.unit7.study.cryptography.labs.lab6.interfaces.Question;
import com.unit7.study.cryptography.labs.lab6.interfaces.QuestionType;
import com.unit7.study.cryptography.labs.lab6.interfaces.Subject;
import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;
import com.unit7.study.cryptography.tools.Pair;

public class Alice implements Subject {
	public Alice(GraphObject object) {
		this.graph = object;
		this.coder = new RSACoder();
		this.coder.setDb(this.coder.getD());
		this.coder.setNb(this.coder.getN());
	}
	
	@Override
	public VerificationData beginVerification() {
		makeIsomorph();
		updateH();
		updateF();
		
		graph.setH(null);	
		return graph;
	}

	@Override
	public VerificationData endVerification(Question question) {
		if (QuestionType.GAMILTONS_CYCLE.equals(question.getType())) {
			return decypherCycle();
		} else if (QuestionType.ISOMORPHIC_GRAPH.equals(question.getType())) {
			return decypherGraph();
		}
		
		throw new IllegalArgumentException("no realisations for input question types");
	}
	
	private void makeIsomorph() {
		// TODO
	}
	
	private void updateH() {
		int[][] g = graph.getG();
		int[][] h = new int[g.length][g[0].length];
		for (int i = 0; i < g.length; ++i) {
			for (int j = 0; j < g[i].length; ++i) {
				h[i][j] = MathUtils.getRandInt(1021314120) * 10 + g[i][j];
			}
		}
		
		graph.setH(h);
	}
	
	private void updateF() {
		int[][] h = graph.getH();
		int[][] f = new int[h.length][h[0].length];
		
		for (int i = 0; i < h.length; ++i) {
			for (int j = 0; j < h[i].length; ++j) {
				f[i][j] = (int) coder.getEncoded(h[i][j]);
			}
		}
		
		graph.setF(f);
	}
	
	private Answer decypherCycle() {
		Map<Pair<Integer, Integer>, Integer> indicies = new HashMap<Pair<Integer, Integer>, Integer>();
				
		// TODO make it
		
		Answer answer = new Answer();
		answer.setIndicies(indicies);
		return answer;
	}
	
	private Answer decypherGraph() {
		Answer answer = new Answer();
		int[][] f = graph.getF();
		int[][] h = new int[f.length][f[0].length];
		
		for (int i = 0; i < f.length; ++i) {
			for (int j = 0; j < f[i].length; ++j) {
				h[i][j] = coder.getDecoded(f[i][j]);
			}
		}
		
		// TODO cycle
		answer.setF(h);
		return answer;
	}
	
	public CoderInfo getCoderInfo() {
		RSACoder other = new RSACoder();
		other.setDb(coder.getDb());
		other.setNb(coder.getNb());
		return other;
	}
	
	private GraphObject graph;
	private List<Pair<Integer, Integer>> cycle;
	private RSACoder coder;
}
