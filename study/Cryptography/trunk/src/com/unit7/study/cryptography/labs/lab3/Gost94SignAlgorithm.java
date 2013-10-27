package com.unit7.study.cryptography.labs.lab3;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Random;

public class Gost94SignAlgorithm implements SignAlgorithm {
    public Gost94SignAlgorithm() {
        generate();
    }

    public void generate() {
        BigInteger[] pq = B();
        p = pq[0];
        q = pq[1];
    }
    
    /**
     * returns p and q by procedure A'
     * @return
     */
    public BigInteger[] A(int t) {
        
    }
        
    /**
     * returns
     * @return
     */
    public BigInteger[] B() {
        
    }
    
    /**
     * returns a by given p and q
     * @return
     */
    public BigInteger C() {
        
    }

    /**
     * first four bytes is an integer whose represent bytes count of r number and
     * next parts of data array is a r and s numbers
     */
    @Override
    public byte[] sign(byte[] data) {
        BigInteger hash = new BigInteger(data);
        
        BigInteger k;
        BigInteger r;

        do {
            k = new BigInteger(q.bitLength() - 2, rnd).add(BigInteger.ONE);
            r = a.modPow(k, p).mod(q);
        } while (r.compareTo(BigInteger.ZERO) == 0);

        BigInteger s = k.multiply(hash).add(x.multiply(r)).mod(q);
        
        byte[] rBytes = r.toByteArray();
        byte[] sBytes = s.toByteArray();
        ByteBuffer buffer = ByteBuffer.allocate(rBytes.length + sBytes.length + 4).
                putInt(rBytes.length).
                put(rBytes).
                put(sBytes);

        return buffer.array();
    }

    @Override
    public boolean verify(byte[] data, byte[] hash) {
        BigInteger h = new BigInteger(hash);
        ByteBuffer buffer = (ByteBuffer) ByteBuffer.allocate(data.length).put(data).position(0);
        int rLength = buffer.getInt();
        byte[] bytes = new byte[rLength];
        buffer.get(bytes, 0, rLength);
        BigInteger r = new BigInteger(bytes);
        bytes = new byte[data.length - 4 - rLength];
        buffer.position(rLength + 4);
        buffer.get(bytes, 0, bytes.length);
        BigInteger s = new BigInteger(bytes);

        if (r.compareTo(BigInteger.ONE) < 0 || r.compareTo(q) >= 0 || s.compareTo(BigInteger.ONE) < 0
                || s.compareTo(q) >= 0) {
            return false;
        }

        BigInteger invH = h.modInverse(q);
        BigInteger u1 = s.multiply(invH).mod(q);
        BigInteger u2 = r.negate().multiply(invH).mod(q);
        BigInteger v = a.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);

        return v.compareTo(r) == 0;
    }

    @Override
    public String getName() {
        return Algorithm.GOST_94.getValue();
    }

    private BigInteger p, q, a, x, y;
    private Random rnd = new Random(System.currentTimeMillis());
}
