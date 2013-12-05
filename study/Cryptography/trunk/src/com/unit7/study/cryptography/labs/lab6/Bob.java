package com.unit7.study.cryptography.labs.lab6;


public class Bob implements Verifier {
	@Override
	public Question verifySubject(VerificationData data) {
		if (!(data instanceof GraphObject)) {
			throw new IllegalArgumentException("data of this type is not supported by Bob");
		}
		
		GraphObject graph = (GraphObject) data;
		return null;
	}

	@Override
	public boolean acceptVerifying(VerificationData data) {
		if (!(data instanceof Answer)) {
			throw new IllegalArgumentException("data of this type is not supported by Bob");
		}
		
		Answer answer = (Answer) data;
		
		return false;
	}

}
