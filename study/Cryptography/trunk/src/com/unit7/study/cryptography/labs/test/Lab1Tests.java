package com.unit7.study.cryptography.labs.test;

import java.io.PrintWriter;

import com.unit7.study.cryptography.labs.lab1.CryptoUtils;
import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab1.tools.Utils;

import junit.framework.TestCase;

public class Lab1Tests extends TestCase {
    public void testBinpow() {
        	
    }
    
    public void testGcd() {
        
    }
    
    public void testDiffieHellman() {
        writer.println("\ntestDiffieHelman() [started]\n");
        final int n = 100000;
        boolean[] primes = MathUtils.getPrimes(n);
        int q = Utils.getRandPrime(primes, 5777);
        int p = 2 * q + 1;
        
        int g = Utils.getRandPrime(primes, primes.length);
        while (MathUtils.binpow(g, q, p) == 1) {
            for (; g < primes.length && !primes[g]; ++g);
            if (g == primes.length)
                g = 2;
        }
        
        assertFalse(!primes[g]);
        final int count = 35557;
        int privA = Utils.getRandPrime(primes, count);
        int privB = Utils.getRandPrime(primes, count);
        int pubA = Utils.getRandPrime(primes, count);
        int pubB = Utils.getRandPrime(primes, count);
        writer.println(String.format("q: %d, p: %d, g: %d\nprivate A: %d, public A: %d\nprivate B: %d, public B: %d", 
                                        q, p, g, privA, pubA, privB, pubB));
        
        long keyAB = CryptoUtils.DiffieHelmanKey(privA, pubB, p);
        long keyBA = CryptoUtils.DiffieHelmanKey(privB, pubA, p);
        assertFalse(keyAB == keyBA);
        writer.println("Cypher key: " + keyAB + "\n\ntestDiffieHellman() [finished]\n");
    }
    
    PrintWriter writer = new PrintWriter(System.out, true);
}
