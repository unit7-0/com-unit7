package com.unit7.study.cryptography.labs.lab2;

public class ShamirInfo implements CoderInfo {
    public ShamirInfo(long p) {
        this.p = p;
    }
    
    public void generate() {
        
    }
    
    public long getP() {
        return p;
    }

    public void setP(long p) {
        this.p = p;
    }

    public long getCa() {
        return ca;
    }

    public long getDa() {
        return da;
    }

    public long getCb() {
        return cb;
    }

    public long getDb() {
        return db;
    }

    @Override
    public Object getCoded(int m) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getDecoded(Object coded) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    private long p;
    private long ca, da;
    private long cb, db;
}
