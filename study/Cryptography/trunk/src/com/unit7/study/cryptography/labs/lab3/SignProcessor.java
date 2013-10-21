package com.unit7.study.cryptography.labs.lab3;

import java.security.NoSuchAlgorithmException;

import com.unit7.study.cryptography.labs.exceptions.UnspecifiedField;

public class SignProcessor {
    public SignProcessor(Signer signer) {
        this.signer = signer;
    }
    
    public SignedData sign(byte[] message) {
        SignedData signedData = new SignedDataImpl();
        signedData.setData(message);
        
        try {
			signer.sign(signedData);
		} catch (UnspecifiedField e) {
			throw new NullPointerException("message");
		}
        
        if (isDetached())
            signedData.setData(null);
        
        return signedData;
    }

    public boolean verify(SignedData data) throws NoSuchAlgorithmException, UnspecifiedField {
        return signer.verify(data);
    }

    public Signer getSigner() {
        return signer;
    }

    public void setSigner(Signer signer) {
        this.signer = signer;
    }

    public boolean isDetached() {
        return detached;
    }

    public void setDetached(boolean detached) {
        this.detached = detached;
    }

    private Signer signer;
    private boolean detached;
}
