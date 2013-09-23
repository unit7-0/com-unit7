package com.unit7.study.cryptography.labs.lab2;

public interface CoderInfo {
    Object getEncoded(int m);
    int getDecoded(Object coded);
}
