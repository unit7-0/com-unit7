package com.unit7.study.cryptography.labs.lab6;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.unit7.study.cryptography.labs.lab6.interfaces.Question;
import com.unit7.study.cryptography.labs.lab6.interfaces.QuestionType;
import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;
import com.unit7.study.cryptography.labs.lab6.interfaces.Verifier;
import com.unit7.study.cryptography.tools.Pair;


public class Bob implements Verifier {
	@Override
	public Question verifySubject(VerificationData data) {
		if (!(data instanceof GraphObject)) {
			throw new IllegalArgumentException("data of this type is not supported by Bob");
		}
		
		try {
			this.data = (GraphObject) ((GraphObject) data).clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Question q = new QuestionImpl();
		QuestionType qType = QuestionType.values()[rnd.nextInt(QuestionType.values().length)];
		q.setType(qType);
		
		this.qType = qType;
		
		return q;
	}

	@Override
	public boolean acceptVerifying(VerificationData data) throws Exception {
		if (!(data instanceof Answer)) {
			throw new IllegalArgumentException("data of this type is not supported by Bob");
		}
		
		Answer answer = (Answer) data;
		
		Map<Pair<Integer, Integer>, Integer> indicies = answer.getIndicies();
		int[][] F = answer.getF();
		
		if (indicies == null || indicies.size() == 0 || (QuestionType.ISOMORPHIC_GRAPH.equals(qType) && F == null)) {
			throw new Exception("answer not matched with question");
		}
		
		// TODO
		
		return false;
	}

	private GraphObject data;
	private Random rnd = new Random();
	private QuestionType qType;
}
