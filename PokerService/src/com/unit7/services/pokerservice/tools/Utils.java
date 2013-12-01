package com.unit7.services.pokerservice.tools;

import java.util.Random;

public class Utils {
    public static int getRandInt(int n) {
        return rnd.nextInt(n);
    }
    
    private static final Random rnd = new Random();
}
