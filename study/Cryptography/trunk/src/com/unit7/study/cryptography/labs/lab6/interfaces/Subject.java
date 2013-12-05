package com.unit7.study.cryptography.labs.lab6.interfaces;

public interface Subject {
	VerificationData beginVerification();
	VerificationData endVerification(Question question);
}
