package com.unit7.study.cryptography.labs.lab2;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab1.tools.Pair;

public class ShamirCoder {
    public ShamirCoder(int p) {
        this.p = p;
        generate();
    }
    
    public void generate() {        
        do {
            c = MathUtils.getRandInt(p - 1);
            Pair<Integer, Integer> xy = new Pair();
            int gcd = MathUtils.gcd(c, p - 1, xy);
            if (gcd != 1) {
                continue;
            }
           
            d = xy.getFirst();
            if (!(d > 2 && MathUtils.gcd(c, d, xy) == 1))
                continue;
            break;
        } while (true);
    }
    
    public long getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public long getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public long getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getCoded(int m) {
        int buf = 0;
        if (first) {
            buf = c;
        } else {
            buf = d;
        }
        
        first = !first;
        return (int) (MathUtils.binpow(m, buf, p) % p);
    }
    
    private int p;
    private int c, d;
    private boolean first = true;
}
