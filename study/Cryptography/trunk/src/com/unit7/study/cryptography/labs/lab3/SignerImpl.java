package com.unit7.study.cryptography.labs.lab3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    @Override
    public void sign(SignedData signedData) throws UnspecifiedField {
        byte[] data = signedData.getData();
        if (data == null)
            throw new UnspecifiedField("signedData.data");

        signedData.setSignature(sign(data));
        signedData.setHash(hasher.getAlgorithm());
        signedData.setCypher(coder.getAlgorithm());
    }

    @Override
    public boolean verify(SignedData signedData) throws UnspecifiedField, NoSuchAlgorithmException {
        byte[] data = signedData.getData();
        byte[] sign = signedData.getSignature();
        if (data == null)
            throw new UnspecifiedField("signedData.data");
        
        if (sign == null)
            throw new UnspecifiedField("signedData.signature");
        
        if (!signedData.getCypher().equals(coder.getAlgorithm()))
            throw new NoSuchAlgorithmException("cypher algorithm signedData not math with signer algorithm");
        
        if (!signedData.getHash().equals(hasher.getAlgorithm()))
            throw new NoSuchAlgorithmException("hash algorithm signedData not math with signer algorithm");
        
        byte[] signature = new byte[data.length + sign.length];
        for (int i = 0; i < signature.length; ++i) {
            if (i < sign.length)
                signature[i] = sign[i];
            else
                signature[i] = data[i - sign.length];
        }
        
        return verify(signature);
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
