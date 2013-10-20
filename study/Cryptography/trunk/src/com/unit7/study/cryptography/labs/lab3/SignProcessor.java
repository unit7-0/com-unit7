package com.unit7.study.cryptography.labs.lab3;

public class SignProcessor {
    public SignProcessor(Signer signer) {
        this.signer = signer;
    }
    
    public byte[] sign(byte[] message) {
        return signer.sign(message);
    }

    public boolean verify(byte[] data) {
        return signer.verify(data);
    }

    public Signer getSigner() {
        return signer;
    }

    public void setSigner(Signer signer) {
        this.signer = signer;
    }

    private Signer signer;
}
