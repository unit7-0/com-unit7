package com.unit7.study.cryptography.labs.lab3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.unit7.study.cryptography.labs.exceptions.UnspecifiedField;
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
    public SignerImpl(SignAlgorithm algorithm) {
        try {
            hasher = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No MD5 implementation");
        }

        this.algorithm = algorithm;
    }

    @Override
    public byte[] sign(byte[] message) {
        byte[] hash = hasher.digest(message);
        byte[] data = algorithm.sign(hash);
        
        this.data = null;
        return data;
    }

    @Override
    public boolean verify(byte[] sign) {
        byte[] hash = hasher.digest(data);
        
        this.data = null;
        return algorithm.verify(sign, hash);
    }

    @Override
    public void sign(SignedData signedData) throws UnspecifiedField {
        byte[] data = signedData.getData();
        if (data == null && this.data == null)
            throw new UnspecifiedField("signedData.data");

        update(data);
        signedData.setSignature(sign(data));
        signedData.setHash(hasher.getAlgorithm());
        signedData.setCypher(algorithm.getName());
    }

    @Override
    public boolean verify(SignedData signedData) throws UnspecifiedField, NoSuchAlgorithmException {
        byte[] data = signedData.getData();
        byte[] sign = signedData.getSignature();
        
        if (sign == null)
            throw new UnspecifiedField("signedData.signature");
        
        if (data == null && this.data == null)
            throw new UnspecifiedField("signedData.data");
        
        if (!signedData.getCypher().equals(algorithm.getName()))
            throw new NoSuchAlgorithmException("cypher algorithm signedData not math with signer algorithm");
        
        if (!signedData.getHash().equals(hasher.getAlgorithm()))
            throw new NoSuchAlgorithmException("hash algorithm signedData not math with signer algorithm");
        
        update(data);        
        return verify(sign);
    }

    public MessageDigest getHasher() {
        return hasher;
    }

    public void setHasher(MessageDigest hasher) {
        this.hasher = hasher;
    }

    public SignAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(SignAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void update(byte[] data) {
        this.data = data;
    }
    
    private MessageDigest hasher;
    private SignAlgorithm algorithm;
    private byte[] data;
}
