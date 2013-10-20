package com.unit7.study.cryptography.labs.lab3;

public class SignedDataImpl implements SignedData {
    private static final long serialVersionUID = -1691872580206900082L;
    
    @Override
    public byte[] getSignature() {
        return sign;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public String getCypher() {
        return cypher;
    }

    @Override
    public void setSignature(byte[] sign) {
        this.sign = sign;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public void setCypher(String cypher) {
        this.cypher = cypher;
    }

    private byte[] data;
    private byte[] sign;
    private String hash;
    private String cypher;
}
