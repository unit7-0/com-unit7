package com.unit7.study.cryptography.labs.lab2;

import java.io.File;

import com.unit7.study.cryptography.labs.lab3.Signer;
import com.unit7.study.cryptography.tools.CoderInfoFactory;

public class CoderInfoFactoryImpl implements CoderInfoFactory {
	@Override
	public CoderInfo createCoderInfo(String type, Object... args) {
		/*switch (type) {
		case Signer.CYPHER_RSA:
			return new RSACoder();
		case Signer.CYPHER_EL_GAMAL:
		    if (!(args instanceof Integer[]) && ((Integer[]) args).length != 4)
		        throw new RuntimeException("args must be int array and his length == 4");
		    
		    Integer[] arr = (Integer[]) args;
			return new ElGamalCoder(arr[0], arr[1], arr[2], arr[3]);
		case Signer.CYPHER_VERNAM:
		    if (!(args[0] instanceof File))
                throw new RuntimeException("args must be File");
		    
			return new VernamCoder((File) args[0]);
		default:
			return null;
		}
	}*/
	    
	    return null;
	}

}
