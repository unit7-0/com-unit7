package com.unit7.study.cryptography.labs.lab6;

public interface Verifier {
	Question verifySubject(VerificationData data);
	boolean acceptVerifying(VerificationData data);
}
