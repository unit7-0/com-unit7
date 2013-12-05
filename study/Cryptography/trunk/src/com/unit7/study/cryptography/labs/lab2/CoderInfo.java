package com.unit7.study.cryptography.labs.lab2;

public interface CoderInfo {
    Object getEncoded(int m);

    int getDecoded(Object coded);

    public String getAlgorithm();
    
    public Object clone() throws CloneNotSupportedException;
}
