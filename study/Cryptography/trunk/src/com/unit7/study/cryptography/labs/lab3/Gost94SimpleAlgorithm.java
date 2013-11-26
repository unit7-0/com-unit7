package com.unit7.study.cryptography.labs.lab3;

import java.math.BigInteger;
import java.util.logging.Logger;

public class Gost94SimpleAlgorithm extends Gost94SignAlgorithm {
    @Override
    public void generate() {
        BigInteger p = null;
        BigInteger q = null;
        BigInteger a = null;
        BigInteger b = null;

        do {
            q = new BigInteger(256, 100, rnd);
            /*log.info(String.format("q is prime: %s, q bitLength: %d, q testBit(255): %s", q.isProbablePrime(100), q.bitLength(),
                    q.testBit(255)));*/
        } while (!q.isProbablePrime(100) || q.bitLength() < 256 || !q.testBit(255));

        do {
            b = new BigInteger(768, rnd);
            b.setBit(767);
            

            p = b.multiply(q).add(BigInteger.ONE);

           /* log.info(String.format(
                    "generated p: %s \r\np bitLength: %d\r\nq: %s\r\nq bitLength: %d \r\nb: %s\r\nb bitLength: %d", p,
                    p.bitLength(), q, q.bitLength(), b, b.bitLength()));*/
        } while (!p.isProbablePrime(100) || p.bitLength() < 1024);
        
        BigInteger g = null;
        do {
            g = new BigInteger(32, rnd);
            BigInteger pow = p.subtract(BigInteger.ONE).divide(q);
            a = g.modPow(pow, p);
        } while (a.compareTo(BigInteger.ONE) == 0);
        
        BigInteger x = null;
        while ((x = new BigInteger(q.bitLength(), rnd))
                .compareTo(BigInteger.ZERO) == 0)
            ;

        BigInteger y = a.modPow(x, p);

        /*
         * log.info(String.format(
         * "generated p: %s \r\np bitLength: %d\r\nq: %s\r\nq bitLength: %d \r\nb: %s\r\nb bitLength: %d"
         * , p, p.bitLength(), q, q.bitLength(), b, b.bitLength()));
         */

        setP(p);
        setQ(q);
        setA(a);
        setX(x);
        setY(y);
    }

    private static final Logger log = Logger.getLogger(Gost94SimpleAlgorithm.class.getName());
}
