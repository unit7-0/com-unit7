package com.unit7.study.cryptography.labs.lab6;

public interface Subject {
	VerificationData beginVerification();
	VerificationData endVerification(Question question);
}
