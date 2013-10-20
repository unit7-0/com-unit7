package com.unit7.study.cryptography.labs.lab3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.unit7.study.cryptography.labs.lab2.CoderInfo;

/**
 * This class gives an interface to sign and verify data with
 * signature(attached). Signature generated with MD5 hash algorithm and attached
 * to the begin of byte array. Cypher algorithm gets from coder.
 * 
 * @author unit7
 * 
 */
public class SignerImpl implements Signer {
    public SignerImpl(CoderInfo coder) {
        try {
            hasher = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No MD5 implementation");
        }

        this.coder = coder;
    }

    @Override
    public byte[] sign(byte[] message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean verify(byte[] data) {
        // TODO Auto-generated method stub
        return false;
    }

    public MessageDigest getHasher() {
        return hasher;
    }

    public void setHasher(MessageDigest hasher) {
        this.hasher = hasher;
    }

    public CoderInfo getCoder() {
        return coder;
    }

    public void setCoder(CoderInfo coder) {
        this.coder = coder;
    }

    private MessageDigest hasher;
    private CoderInfo coder;
}
