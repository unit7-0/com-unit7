package com.unit7.study.cryptography.labs.lab3;

import java.security.NoSuchAlgorithmException;

import com.unit7.study.cryptography.labs.exceptions.UnspecifiedField;

public interface Signer {
    byte[] sign(byte[] message);

    /**
     * verify data with @param sign
     * 
     * @param sign
     * @return
     */
    boolean verify(byte[] sign);

    void sign(SignedData signedData) throws UnspecifiedField;

    boolean verify(SignedData signedData) throws UnspecifiedField, NoSuchAlgorithmException;

    /**
     * update data to verify
     * 
     * @param data
     */
    void update(byte[] data);
}
