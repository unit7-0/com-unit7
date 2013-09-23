package com.unit7.study.cryptography.labs.lab2;

import java.math.BigInteger;

import com.unit7.study.cryptography.labs.lab1.MathUtils;

public class ShamirCoder {
    public ShamirCoder(int p) {
        this.p = p;
        generate();
    }
    
    public void generate() {
        BigInteger test;
        boolean recieved = false;
        
        do {
            int number = MathUtils.getRandInt(p + 1);
            test = new BigInteger(String.valueOf(number));
            if (test.isProbablePrime(6) || (number % (p - 1)) != 1)
                continue;
            int root = (int) (Math.sqrt(number) + 1);
            for (int i = 2; i <= root; ++i) {
                if (number % i == 0) {
                    c = i;
                    d = number / i;
                    recieved = true;
                    break;
                }
            }
        } while (!recieved);
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
            first = false;
        } else {
            buf = d;
            first = true;
        }
        
        return (int) (MathUtils.binpow(m, buf, p) % p);
    }
    
    private int p;
    private int c, d;
    private boolean first = true;
}
