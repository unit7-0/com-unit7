package com.unit7.study.cryptography.labs.lab3;

public interface Signer {
    byte[] sign(byte[] message);
    boolean verify(byte[] data);
}
