package com.unit7.study.cryptography.labs.lab2;

import java.io.File;

import com.unit7.study.cryptography.labs.lab3.Algorithm;
import com.unit7.study.cryptography.tools.CoderInfoFactory;

public class CoderInfoFactoryImpl implements CoderInfoFactory {
	@Override
	public CoderInfo createCoderInfo(Algorithm type, Object... args) {
	    if (Algorithm.RSA.equals(type)) {
	        RSACoder coder = new RSACoder();
	        coder.setDb(coder.getD());
	        coder.setNb(coder.getN());
	        return coder;
	    } else if (Algorithm.EL_GAMAL.equals(type)) {
	        if (!(args instanceof Integer[]) && ((Integer[]) args).length != 4)
                throw new RuntimeException("args must be int array and his length == 4");
            
            Integer[] arr = (Integer[]) args;
            return new ElGamalCoder(arr[0], arr[1], arr[2], arr[3]);
	    } else if (Algorithm.VERNAM.equals(type)) {
	        if (!(args[0] instanceof File))
                throw new RuntimeException("args must be File");
            
            return new VernamCoder((File) args[0]);
	    }
	    
	    return null;
	}

}
