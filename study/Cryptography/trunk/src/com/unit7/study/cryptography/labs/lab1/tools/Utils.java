package com.unit7.study.cryptography.labs.lab1.tools;

import java.math.BigInteger;
import java.util.Random;

import com.unit7.study.cryptography.labs.lab1.MathUtils;

public class Utils {
	/**
	 * 
	 * @param arr
	 * @param n
	 * @return 0..min(arr.length, n)
	 */
	public static <T> T getRand(T[] arr, int n) {
		n = Math.max(n, 0);
		return arr[random.nextInt(Math.min(arr.length, n))];
	}

	public static int getRandPrime(boolean[] arr, int n) {
		if (n < 2)
			throw new IllegalArgumentException("n < 2");
		if (arr.length < 3)
			throw new IllegalArgumentException("arr.length < 3");

		int rand = random.nextInt(Math.min(arr.length, n));
		int res = rand;
		for (; res < arr.length; ++res) {
			if (arr[res])
				return res;
		}

		res = rand - 1;
		for (; res >= 0; --res) {
			if (arr[res])
				return res;
		}

		throw new IllegalArgumentException("arr not a prime array");
	}

	public static Pair<Integer, Integer> generatePG(int n) {
	    int q;
        int p;
        BigInteger testP;
        
        do {
            q = MathUtils.getRandPrime(n);
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
        
        return new Pair(p, g);
	}
	
	public static int getRandPrime(int n) {
		return getRandPrime(primes, n);
	}

	public static void setPrimesCount(int n) {
		primes = MathUtils.getPrimes(n);
	}
	
	public static int getPrimesCount() {
		return primes.length;
	}
	
	public static boolean isPrime(int cand) {
		return primes[cand];
	}
	
	private static Random random = new Random(System.currentTimeMillis());
	private static boolean[] primes = MathUtils.getPrimes(100000);
}
