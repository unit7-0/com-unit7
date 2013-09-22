package com.unit7.study.cryptography.labs.test;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import com.unit7.study.cryptography.labs.lab1.CryptoUtils;
import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab1.tools.CryptoData;
import com.unit7.study.cryptography.labs.lab1.tools.Pair;
import com.unit7.study.cryptography.labs.lab1.tools.Utils;

import junit.framework.TestCase;

public class Lab1Tests extends TestCase {
    public void testBinpow() {
        writer.println("testBinpow() [started]\n");

        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; ++i) {
            int a = rand.nextInt(50);
            int d = rand.nextInt(50);
            int mod = rand.nextInt(100000);
            int res = (int) MathUtils.binpow(a, d, mod);
            writer.println(a + "^" + d + "%" + mod + "=" + res);
        }
        
        writer.println("\ntestBinpow() [started]");
    }

    public void testGcd() {
        writer.println("testGcd() [started]\n");
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; ++i) {
            int a = rand.nextInt(40);
            int b = rand.nextInt(255);
            Pair<Integer, Integer> xy = new Pair();
            int gcd = MathUtils.gcd(a, b, xy);
            writer.println("gcd(" + a + ", " + b + ") = " + a + " * " + xy.getFirst() + " + " + b
                    + " * " + xy.getSecond() + " = " + gcd);
        }
        
        writer.println("\ntestGcd() [finished]");
    }

    public void testDiffieHellman() {
        writer.println("\ntestDiffieHelman() [started]\n");
        int q;
        int p;
        BigInteger testP;
        
        do {
            q = MathUtils.getRandPrime(13335);
            p = 2 * q + 1;
            testP = new BigInteger(String.valueOf(p));
        } while(!testP.isProbablePrime(6));

        int g = 2;
        while (MathUtils.binpow(g, q, p) == 1) {
            for (int i = g + 1; i < p; ++i) {
                if (Utils.isPrime(i)) {
                    g = i;
                    break;
                }
            }
        }

        CryptoData cryptoDataFirst = new CryptoData(p, g);
        CryptoData cryptoDataSecond = new CryptoData(p, g);
        writer.println(String
                .format("q: %d, p: %d, g: %d\nprivate A: %d, public A: %d\nprivate B: %d, public B: %d",
                        q, cryptoDataFirst.getP(), cryptoDataFirst.getG(),
                        cryptoDataFirst.getPrivate(),
                        cryptoDataFirst.getPublic(),
                        cryptoDataSecond.getPrivate(),
                        cryptoDataSecond.getPublic()));

        long keyAB = CryptoUtils.DiffieHellmanKey(cryptoDataFirst.getPrivate(),
                cryptoDataSecond.getPublic(), p);
        long keyBA = CryptoUtils.DiffieHellmanKey(cryptoDataSecond.getPrivate(),
                cryptoDataFirst.getPublic(), p);

        assertTrue(keyAB == keyBA);

        writer.println("Cypher key: " + keyAB
                + "\n\ntestDiffieHellman() [finished]\n");
    }

    public void testBabyStepGiantStep() {
        writer.println("\ntestBabyStepGianStep() [started]\n");

        int q = Utils.getRandPrime(5777);
        int p = 2 * q + 1;

        int g = Utils.getRandPrime(p);
        while (MathUtils.binpow(g, q, p) == 1) {
            for (int i = g + 1; i < p; ++i) {
                if (Utils.isPrime(i)) {
                    g = i;
                    break;
                }
            }
        }

        CryptoData cryptoDataFirst = new CryptoData(p, g);
        CryptoData cryptoDataSecond = new CryptoData(p, g);

        long keyAB = CryptoUtils.DiffieHellmanKey(cryptoDataFirst.getPrivate(),
                cryptoDataSecond.getPublic(), p);
        long keyBA = CryptoUtils.DiffieHellmanKey(cryptoDataSecond.getPrivate(),
                cryptoDataFirst.getPublic(), p);

        assertTrue(keyAB == keyBA);

        List<Long> privateA = CryptoUtils.babyStepGiantStep(
                cryptoDataSecond.getPublic(), keyAB, p);
        List<Long> privateB = CryptoUtils.babyStepGiantStep(
                cryptoDataFirst.getPublic(), keyAB, p);

        writer.println("PrivateA: " + cryptoDataFirst.getPrivate()
                + " decyphered: "
                + privateA.contains((long) cryptoDataFirst.getPrivate())
                + "\nPrivateB: " + cryptoDataSecond.getPrivate()
                + " decyphered: "
                + privateB.contains((long) cryptoDataSecond.getPrivate()));

        writer.println("Decyphering keys:\nPrivateA: " + privateA
                + "\nPrivateB: " + privateB);
        writer.println("\ntestBabyStepGiatStep() [finished]");
    }

    private PrintWriter writer = new PrintWriter(System.out, true);
}
