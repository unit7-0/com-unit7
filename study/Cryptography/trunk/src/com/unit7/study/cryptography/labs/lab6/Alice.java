package com.unit7.study.cryptography.labs.lab6;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab2.RSACoder;
import com.unit7.study.cryptography.labs.lab6.interfaces.Question;
import com.unit7.study.cryptography.labs.lab6.interfaces.QuestionType;
import com.unit7.study.cryptography.labs.lab6.interfaces.Subject;
import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;

public class Alice implements Subject {
	public Alice(GraphObject object) {
		this.graph = object;
		this.coder = new RSACoder();
		this.coder.setDb(this.coder.getD());
		this.coder.setNb(this.coder.getN());
	}
	
	@Override
	public VerificationData beginVerification() {
		updateH();
		updateF();
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
		// TODO
		return new Answer();
	}
	
	private Answer decypherGraph() {
		// TODO
		return new Answer();
	}
	
	public CoderInfo getCoderInfo() {
		RSACoder other = new RSACoder();
		other.setDb(coder.getDb());
		other.setNb(coder.getNb());
		return other;
	}
	
	private GraphObject graph;
	private RSACoder coder;
}
