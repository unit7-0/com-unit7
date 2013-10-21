package com.unit7.study.cryptography.labs.lab2;

import com.unit7.study.cryptography.labs.lab3.Signer;
import com.unit7.study.cryptography.tools.CoderInfoFactory;

public class CoderInfoFactoryImpl implements CoderInfoFactory {
	@Override
	public CoderInfo createCoderInfo(String type, Object... args) {
		switch (type) {
		case Signer.CYPHER_RSA:
			return new RSACoder();
		case Signer.CYPHER_EL_GAMAL:
			//return new ElGamalCoder(args);
		case Signer.CYPHER_VERNAM:
			//return new VernamCoder(args);
		default:
			return null;
		}
	}

}
