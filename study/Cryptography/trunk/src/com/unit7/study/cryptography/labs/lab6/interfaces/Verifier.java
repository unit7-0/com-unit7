package com.unit7.study.cryptography.labs.lab6.interfaces;

public interface Verifier {
	Question verifySubject(VerificationData data);
	boolean acceptVerifying(VerificationData data);
}
