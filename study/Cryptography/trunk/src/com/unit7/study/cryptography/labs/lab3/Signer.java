package com.unit7.study.cryptography.labs.lab3;

import java.security.NoSuchAlgorithmException;

import com.unit7.study.cryptography.labs.exceptions.UnspecifiedField;

public interface Signer {
    byte[] sign(byte[] message);

    boolean verify(byte[] data);

    void sign(SignedData signedData) throws UnspecifiedField;

    boolean verify(SignedData signedData) throws UnspecifiedField, NoSuchAlgorithmException;

    public static final String CYPHER_RSA = "RSA";
    public static final String CYPHER_GOST = "GOST";
    public static final String CYPHER_EL_GAMAL = "EL_GAMAL";
}
