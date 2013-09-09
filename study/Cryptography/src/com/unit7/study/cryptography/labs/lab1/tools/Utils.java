package com.unit7.study.cryptography.labs.lab1.tools;

import java.util.Random;

public class Utils {
    /**
     * 
     * @param arr
     * @param n
     * @return
     * 0..min(arr.length, n)
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
    
    private static Random random = new Random(System.currentTimeMillis());
}
