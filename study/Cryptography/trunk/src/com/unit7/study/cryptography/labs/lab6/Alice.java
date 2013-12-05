package com.unit7.study.cryptography.labs.lab6;

import com.unit7.study.cryptography.labs.lab6.interfaces.QuestionType;

public class Alice implements Subject {
	public Alice(GraphObject object) {
		this.graph = object;
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
		
	}
	
	private void updateF() {
		
	}
	
	private Answer decypherCycle() {
		// TODO
		return new Answer();
	}
	
	private Answer decypherGraph() {
		// TODO
		return new Answer();
	}
	
	private GraphObject graph;
}
