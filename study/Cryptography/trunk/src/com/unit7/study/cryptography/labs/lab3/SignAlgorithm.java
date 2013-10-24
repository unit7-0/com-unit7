package com.unit7.study.cryptography.labs.lab3;

public interface SignAlgorithm {
    byte[] sign(byte[] data);

    boolean verify(byte[] data, byte[] hash);

    String getName();
}
