package com.unit7.study.cryptography.labs.lab1;

import java.util.Arrays;

import com.unit7.study.cryptography.labs.lab1.tools.Pair;

public class MathUtils {
    public static long binpow(long x, long a, long mod) {
        long res = 1;
        for (; a > 0; a >>= 1) {
            if ((a & 1) == 1)
                res = (res * x) % mod;
            
            x = (x * x) % mod;
        }
        
        return res;
    }
    
    public static int gcd(int a, int b, Pair<Integer, Integer> xy) {
        if (a == 0) {
            xy.setFirst(0);
            xy.setSecond(1);
            return b;
        }
        
        Pair<Integer, Integer> xy1 = new Pair<Integer, Integer>();
        int d = gcd(b % a, a, xy1);
        xy.setFirst(xy1.getSecond() - (b / a) * xy1.getFirst());
        xy.setSecond(xy1.getFirst());
        
        return d;
    }
    
    public static boolean[] getPrimes(int n) {
        boolean[] primes = new boolean[n + 1];
        Arrays.fill(primes, true);
        primes[0] = primes[1] = false;
        
        for (int i = 2; i <= Math.sqrt(n); ++i) {
            if (primes[i]) {
                for (long j = i * i; j <= n; j += i) {
                    primes[(int) j] = false;
                }
            }
        }
        
        return primes;
    }
    
    public static int getPrime(int n) {
        // TODO realise
        return 0;
    }
}
