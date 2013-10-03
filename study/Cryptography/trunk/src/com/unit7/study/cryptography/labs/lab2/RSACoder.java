package com.unit7.study.cryptography.labs.lab2;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab1.tools.Pair;

public class RSACoder implements CoderInfo {
    public RSACoder() {
        generate();
    }
    
    public void generate() {
        int p = MathUtils.getRandPrime(13377);
        int q = MathUtils.getRandPrime(13377);
        n = p * q;
        int gcd = 0;
        int f = (p - 1) * (q - 1);
        Pair<Integer, Integer> xy = new Pair<Integer, Integer>();
        do {
            d = MathUtils.getRandInt(f);
            gcd = MathUtils.gcd(d, f, xy);
        } while(gcd != 1);
        
        c = xy.getFirst();
        if (c < 0)
            c += f;
    }
    
    @Override
    public Object getEncoded(int m) {
        return (int) MathUtils.binpow(m, db, nb);
    }

    @Override
    public int getDecoded(Object coded) {
        if (!(coded instanceof Integer))
            throw new IllegalArgumentException("coded parameter must be int");
        
        return (int) MathUtils.binpow((Integer) coded, c, n);
    }
    
    public int getN() {
        return n;
    }

    public int getD() {
        return d;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    private int c, n, d;
    private int db, nb;
}
