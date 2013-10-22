package com.unit7.study.cryptography.labs.lab2;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab3.Signer;

public class ElGamalCoder implements CoderInfo {
    public ElGamalCoder(int p, int g, int selfPriv, int pub) {
        this.p = p;
        this.g = g;
        this.selfPriv = selfPriv;
        this.pub = pub;
    }
    
    @Override
    public Object getEncoded(int m) {
        // 1 <= k <= p - 2
        int k = MathUtils.getRandInt(p - 2) + 1;
        int r = (int) MathUtils.binpow(g, k, p);
        int e = (int) (m * MathUtils.binpow(pub, k, p) % p);
        return new Integer[] { r, e };
    }

    @Override
    public int getDecoded(Object coded) {
        if (!(coded instanceof Integer[]))
            return -1;
            //throw new IllegalArgumentException("coded arg must be Integer array");
        
        Integer[] codes = (Integer[]) coded;
        if (codes.length != 2)
            throw new IllegalArgumentException("Required two integer parameters");
    
        int r = codes[0];
        int e = codes[1];
        int m = (int) (e * MathUtils.binpow(r, p - 1 - selfPriv, p) % p);
        
        return m;
    }
    
    @Override
    public String getAlgorithm() {
        return Signer.CYPHER_EL_GAMAL;
    }
    
    private int p, g, selfPriv, pub;
}
