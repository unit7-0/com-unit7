package com.unit7.study.cryptography.labs.lab2;


public class VernamCoder implements CoderInfo {
    public VernamCoder(int k) {
        this.k = k;
    }
    
    @Override
    public Object getEncoded(int m) {
        return m ^ k;
    }

    @Override
    public int getDecoded(Object coded) {
        if (!(coded instanceof Integer))
            throw new IllegalArgumentException("coded parameter must be int");
        
        return (Integer) coded ^ k;
    }
    
    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    private int k;
}
